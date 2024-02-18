package edu.java.bot;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BotTest {
    @Test
    void test1(){
        /*
        //arrange
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();

        Field idField = chat.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(chat, 1L);

        Field textField = message.getClass().getDeclaredField("text");
        textField.setAccessible(true);
        textField.set(message, "/start");
        Field chatField = message.getClass().getDeclaredField("sender_chat");
        chatField.setAccessible(true);
        chatField.set(message, chat);

        Field messageField = update.getClass().getDeclaredField("message");
        messageField.setAccessible(true);
        messageField.set(update, message);

        System.out.println(update.message().getClass());
        */

        //act
        var mock = Mockito.mock(Update.class);
        Mockito.when(mock.message()).thenReturn(Mockito.mock(Message.class));
        Mockito.when(mock.message().chat()).thenReturn(Mockito.mock(Chat.class));
        Mockito.when(mock.message().chat().id()).thenReturn(1L);
        Mockito.when(mock.message().text()).thenReturn("/start");

        Bot bot = new Bot("test");
        var act = bot.process(List.of(mock));
        //assert
        assertThat(act).isEqualTo(UpdatesListener.CONFIRMED_UPDATES_ALL);
    }
}
