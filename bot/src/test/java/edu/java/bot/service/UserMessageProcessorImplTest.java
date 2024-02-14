package edu.java.bot.service;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.comand.Command;
import edu.java.bot.service.impl.UserMessageProcessorImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class UserMessageProcessorImplTest {

    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private Chat chat;
    @InjectMocks
    private UserMessageProcessorImpl userMessageProcessor;

    private final Long chatId = 123L;
    private final List<Command> commandList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        userMessageProcessor = new UserMessageProcessorImpl(commandList);
    }

    @Test
    void testWithCommand() {
        Command command1 = mock(Command.class);
        given(command1.supports(update)).willReturn(true);
        SendMessage givenSendMessage = new SendMessage(chatId, "/start - Start the bot and get welcome message");
        given(command1.handle(update)).willReturn(givenSendMessage);
        commandList.add(command1);
        SendMessage result = userMessageProcessor.process(update);

        String text = (String) result.getParameters().get("text");
        assertThat(text).isEqualTo("/start - Start the bot and get welcome message");
    }

    @Test
    void testWithNonexistentCommand() {
        given(update.message()).willReturn(message);
        given(message.chat()).willReturn(chat);
        given(chat.id()).willReturn(chatId);

        SendMessage result = userMessageProcessor.process(update);

        String text = (String) result.getParameters().get("text");
        assertThat(text).isEqualTo("Sorry, I don't understand this command.");
    }
}
