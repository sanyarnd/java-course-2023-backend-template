import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.ChatService;
import edu.java.bot.exceptions.ResolvingException;
import edu.java.bot.strategy.ListCommandStrategy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest(classes = {edu.java.bot.strategy.ListCommandStrategy.class, edu.java.bot.service.ChatService.class,
    edu.java.bot.repository.ChatRepository.class})
public class ListCommandStrategyTest {

    @Spy
    Update update;

    @Spy
    Message message;

    @Spy
    Chat chat;
    @Autowired ChatService chatService;

    @Autowired
    ListCommandStrategy listCommandStrategy;

    Long registeredId = 1L;

    @Test
    void checkListOnlyForRegistered() throws ResolvingException {
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(update.message().chat()).thenReturn(chat);
        Mockito.when(update.message().chat().id()).thenReturn(registeredId);
        chatService.register(registeredId);
        chatService.trackUrl(registeredId, "lol");
        chatService.trackUrl(registeredId, "kek");
        chatService.trackUrl(registeredId, "mem");
        Assertions.assertThat(listCommandStrategy.resolve(update)).isEqualTo(List.of("lol", "kek", "mem").toString());

    }
}
