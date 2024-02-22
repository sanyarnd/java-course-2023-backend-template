package edu.java.bot.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.TestUtils;
import edu.java.bot.UpdateMock;
import edu.java.bot.service.ChatService;
import edu.java.bot.service.command.StartCommand;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StartCommandTest {
    @Mock
    private ChatService chatService;
    private StartCommand startCommand;
    private final static String WELCOME = "Hello! Welcome to our bot!";
    private final static String WELCOME_AGAIN =
        "You are already working with our bot. Use /help to see a list of all possible commands";
    private final static String DESCRIPTION = "Start working with the bot";

    @BeforeEach
    public void setUp() {
        startCommand = new StartCommand(chatService);
    }

    @Test
    public void testOneMessage() {
        Update update = new UpdateMock().withChat().build();

        SendMessage sendMessage = startCommand.process(update);
        TestUtils.checkMessage(sendMessage, WELCOME);

    }

    @Test
    public void testTwoMessages() {
        Update update1 = new UpdateMock().withChat().build();
        final long chatId = update1.message().chat().id();
        SendMessage sendMessage1 = startCommand.process(update1);
        TestUtils.checkMessage(sendMessage1, WELCOME);

        Update update2 = new UpdateMock().withChat(chatId).build();
        when(chatService.find(chatId)).thenReturn(Optional.of(new edu.java.bot.domain.Chat(chatId, List.of())));
        SendMessage sendMessage2 = startCommand.process(update2);
        TestUtils.checkMessage(sendMessage2, WELCOME_AGAIN);
    }

    @Test
    public void testTwoSenders() {
        Update updateFrom1 = new UpdateMock().withChat().build();
        SendMessage sendMessage1 = startCommand.process(updateFrom1);
        TestUtils.checkMessage(sendMessage1, WELCOME);

        Update updateFrom2 = new UpdateMock().withChat().build();
        when(chatService.find(updateFrom2.message().chat().id())).thenReturn(Optional.empty());
        SendMessage sendMessage2 = startCommand.process(updateFrom2);
        TestUtils.checkMessage(sendMessage2, WELCOME);
    }

    @Test
    public void testSupports() {
        assertThat(startCommand.supports(new UpdateMock().withMessage("/start").build())).isTrue();
        assertThat(startCommand.supports(new UpdateMock().withMessage("/help").build())).isFalse();
    }

    @Test
    public void testCommandAndDescription() {
        assertThat(startCommand.command()).isNotNull().isEqualTo("start");
        assertThat(startCommand.description()).isNotNull().isEqualTo(DESCRIPTION);
    }

    @Test
    public void testToApiCommand() {
        assertThat(startCommand.toApiCommand()).isNotNull().isEqualTo(new BotCommand("start", DESCRIPTION));
    }
}
