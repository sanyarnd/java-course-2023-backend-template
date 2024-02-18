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
public class TrackCommandTest {
    public static Update update;
    public static Message message;
    public static Chat chat;

    @Autowired
    public Command trackCommand;


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
//    public void TrackCommandTestWithRightInput() {
//        Mockito.when(update.message()).thenReturn(message);
//        Mockito.when(message.text()).thenReturn("/track https://github.com/imponomarev");
//        Mockito.when(message.chat()).thenReturn(chat);
//        Mockito.when(chat.id()).thenReturn(11L);
//        mapStorage.registrate(update);
//        SendMessage sendMessage = trackCommand.handle(update);
//        Assertions.assertEquals(
//            sendMessage.getParameters().get("text"),
//            "you have successfully subscribed to the resource"
//        );
//        Assertions.assertEquals(
//            sendMessage.getParameters().get("chat_id"),
//            11L
//        );
//    }
}
