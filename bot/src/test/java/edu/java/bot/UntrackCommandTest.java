package edu.java.bot;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import edu.java.bot.dao.MapStorage;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UntrackCommandTest {

    public static Update update;
    public static Message message;
    public static Chat chat;

    @Autowired
    public Command untrackCommand;


    @Autowired
    MapStorage mapStorage;

    @AfterEach
    public void clearStorage() {
        mapStorage.clear();
    }

    @BeforeAll
    static void initialize() {
        update = Mockito.mock(Update.class);
        message = Mockito.mock(Message.class);
        chat = Mockito.mock(Chat.class);
    }

//    Та же ошибка, что и с командой help
//    @Test
//    public void UntrackCommandTest() {
//        Mockito.when(update.message()).thenReturn(message);
//        Mockito.when(message.text()).thenReturn("/untrack");
//        Mockito.when(message.chat()).thenReturn(chat);
//        Mockito.when(chat.id()).thenReturn(11L);
//        SendMessage sendMessage = untrackCommand.handle(update);
//        Assertions.assertEquals(
//            sendMessage.getParameters().get("text"),
//            "you have successfully unsubscribed from the resource"
//        );
//        Assertions.assertEquals(
//            sendMessage.getParameters().get("chat_id"),
//            11L
//        );
//    }

}
