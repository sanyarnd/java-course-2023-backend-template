import edu.java.bot.exceptions.ResolvingException;
import edu.java.bot.strategy.HelpCommandStrategy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {edu.java.bot.strategy.HelpCommandStrategy.class})
public class HelpCommandStrategyTest {
    @Autowired
    HelpCommandStrategy helpCommandStrategy;


    @Test
    @DisplayName("help command returns all possible commands")
    void getAllPossibleCommands() throws ResolvingException {
        Assertions.assertThat(helpCommandStrategy.resolve(null))
            .isEqualTo("[/help - prints list of all commands, /start - registers you in the system, " +
                "/track - tracks one more site, /untrack - untracks one more site, /list - lists all sites]");
    }
}
