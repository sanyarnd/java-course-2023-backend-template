package edu.java.bot.resolver;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.command.CommandChain;
import edu.java.bot.command.HelpCommand;
import edu.java.bot.service.CommandService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UpdateResolverTest {

    private final UpdateResolver updateResolver =
        UpdateResolver.link(
            new UpdateMessageResolver(new CommandChain(new HelpCommand())),
            new UpdateCallbackResolver(new CommandService())
        );

    @Test
    @DisplayName("UpdateResolver#resolve with invalid Update test")
    public void test_shouldThrowException_whenUpdateIsInvalid() {
        Assertions.assertThatThrownBy(() -> updateResolver.resolve(new Update())).isInstanceOf(RuntimeException.class);
    }
}
