import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.exceptions.ResolvingException;
import edu.java.bot.strategy.StartCommandStrategy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {edu.java.bot.strategy.StartCommandStrategy.class, edu.java.bot.service.ChatService.class,
    edu.java.bot.repository.ChatRepository.class})
public class StartCommandStrategyTest {
    @Autowired
    StartCommandStrategy strategy;

    @Spy
    Update update;

    @Spy
    Message message;

    @Spy
    Chat chat;
    Long registeredId = 1L;

    @Test
    public void checkStartWorksNotIdempotent() throws ResolvingException {
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(update.message().chat()).thenReturn(chat);
        Mockito.when(update.message().chat().id()).thenReturn(registeredId);

        Assertions.assertThat(strategy.resolve(update))
            .isEqualTo("Hello! you've successfully registered. type /help to see all commands");

        Assertions.assertThat(strategy.resolve(update))
            .isEqualTo("Oh, you've been registered previously");
    }

}
