package edu.java.bot.command;



import com.pengrad.telegrambot.request.SendMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.java.bot.command.Command.HELP;
import static edu.java.bot.util.MessagesUtils.HELP_MESSAGE;

public class HelpCommandTest {

    @Test
    @DisplayName("HelpCommandExecutor#execute test")
    public void execute_shouldReturnCorrectMessage() {
        HelpCommand testExecutor = new HelpCommand();

        SendMessage actual = testExecutor.execute(HELP.getCommandName(), 1);

        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(HELP_MESSAGE);
        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1L);
    }
}
