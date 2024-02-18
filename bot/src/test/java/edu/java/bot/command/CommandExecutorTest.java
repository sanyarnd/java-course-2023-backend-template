package edu.java.bot.command;


import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.CommandService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static edu.java.bot.util.MessagesUtils.ERROR_MESSAGE;

public class CommandExecutorTest {

    @Mock
    private CommandService linkService;
    private final CommandExecutor commandExecutor = CommandExecutor.link(
        new StartCommand(),
        new HelpCommand(),
        new ListCommand(linkService),
        new TrackCommand(linkService),
        new UntrackCommand(linkService)
    );

    @Test
    @DisplayName("CommandExecutor#execute test")
    public void execute_shouldReturnCorrectMessage_whenCommandIsInvalid() {
        SendMessage actual = commandExecutor.execute("open", 1);

        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(ERROR_MESSAGE);
    }
}
