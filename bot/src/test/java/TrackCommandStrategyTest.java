import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.BotState;
import edu.java.bot.service.ChatService;
import edu.java.bot.exceptions.ResolvingException;
import edu.java.bot.strategy.TrackCommandStrategy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {edu.java.bot.strategy.TrackCommandStrategy.class, edu.java.bot.service.ChatService.class,
    edu.java.bot.repository.ChatRepository.class})
public class TrackCommandStrategyTest {
    @Spy
    Update update;

    @Spy
    Message message;

    @Spy
    Chat chat;
    @Autowired ChatService chatService;

    @Autowired
    TrackCommandStrategy trackCommandStrategy;


    Long registeredId = 1L;


    @Test
    void TrackNewRef() throws ResolvingException {
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(update.message().text()).thenReturn("/track");
        Mockito.when(update.message().chat()).thenReturn(chat);
        Mockito.when(update.message().chat().id()).thenReturn(registeredId);
        chatService.register(registeredId);


        Assertions.assertThat(chatService.findChat(registeredId)).isNotEmpty();
        Assertions.assertThat(chatService.findChat(registeredId).get().getBotState()).isEqualTo(BotState.READY);

        Assertions.assertThat(trackCommandStrategy.resolve(update))
            .isEqualTo("Please enter url you want to track");

        Assertions.assertThat(chatService.findChat(registeredId)).isNotEmpty();
        Assertions.assertThat(chatService.findChat(registeredId).get().getBotState()).isEqualTo(BotState.TRACK);


        Mockito.when(update.message().text()).thenReturn("SomeRef");

        Assertions.assertThat(trackCommandStrategy.resolve(update))
            .isEqualTo("Url successfully tracked");

        Assertions.assertThat(chatService.findChat(registeredId).get().getBotState()).isEqualTo(BotState.READY);

    }

}
