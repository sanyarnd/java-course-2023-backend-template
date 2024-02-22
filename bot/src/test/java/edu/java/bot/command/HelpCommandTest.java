package edu.java.bot.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.TestUtils;
import edu.java.bot.UpdateMock;
import edu.java.bot.service.command.Command;
import edu.java.bot.service.command.HelpCommand;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HelpCommandTest {

    @Mock
    private List<Command> commands;
    private HelpCommand helpCommand;
    private static final String DESCRIPTION = "Show all possible commands";

    @BeforeEach
    public void setUp() {
        helpCommand = new HelpCommand(commands);
    }

    @Test
    public void testOneMessage() {
        generateCommands(3);
        Update update = new UpdateMock().withChat().build();
        SendMessage sendMessage = helpCommand.process(update);
        TestUtils.checkMessage(sendMessage, generateDescription(3));
    }

    @Test
    public void testTwoMessages() {
        generateCommands(4);
        final String expectedDescription = generateDescription(4);
        Update update1 = new UpdateMock().withChat().build();
        final long chatId = update1.message().chat().id();
        SendMessage sendMessage1 = helpCommand.process(update1);
        TestUtils.checkMessage(sendMessage1, expectedDescription);

        Update update2 = new UpdateMock().withChat(chatId).build();
        SendMessage sendMessage2 = helpCommand.process(update2);
        TestUtils.checkMessage(sendMessage2, expectedDescription);
    }

    @Test
    public void testTwoSenders() {
        generateCommands(5);
        final String expectedDescription = generateDescription(5);
        Update update1 = new UpdateMock().withChat().build();
        SendMessage sendMessage1 = helpCommand.process(update1);
        TestUtils.checkMessage(sendMessage1, expectedDescription);

        Update update2 = new UpdateMock().withChat().build();
        SendMessage sendMessage2 = helpCommand.process(update2);
        TestUtils.checkMessage(sendMessage2, expectedDescription);
    }

    @Test
    public void testSupports() {
        assertThat(helpCommand.supports(new UpdateMock().withMessage("/help").build())).isTrue();
        assertThat(helpCommand.supports(new UpdateMock().withMessage("/start").build())).isFalse();
    }

    @Test
    public void testCommandAndDescription() {
        assertThat(helpCommand.command()).isNotNull().isEqualTo("help");
        assertThat(helpCommand.description()).isNotNull().isEqualTo(DESCRIPTION);
    }

    @Test
    public void testToApiCommand() {
        assertThat(helpCommand.toApiCommand()).isNotNull().isEqualTo(new BotCommand("help", DESCRIPTION));
    }

    private void generateCommands(int number) {
        for (int i = 0; i < number; i++) {
            Command command = mock(Command.class);
            when(command.toString()).thenReturn("/cmd" + (i + 1) + " - Description " + (i + 1));
            when(commands.get(i)).thenReturn(command);
        }
        when(commands.size()).thenReturn(number);
    }

    private String generateDescription(int number) {
        return "List of all possible commands:\n" + "1. /help - " + DESCRIPTION + "\n" +
            IntStream.range(1, number + 1).mapToObj(i -> (i + 1) + ". /cmd" + i + " - Description " + i).collect(
                Collectors.joining("\n"));
    }
}
