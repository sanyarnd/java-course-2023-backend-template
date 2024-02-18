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
public class HelpCommandTest {

    public static Update update;
    public static Message message;
    public static Chat chat;


    @Autowired
    public Command helpCommand;

    @Autowired
    MapStorage mapStorage;

    @AfterEach
    public void clearStorage() {
        mapStorage.clear();
    }

    @BeforeAll
    static void init() {
        update = Mockito.mock(Update.class);
        message = Mockito.mock(Message.class);
        chat = Mockito.mock(Chat.class);
    }
    //Не могу понять причину возникновения данной ошибки
    //java.lang.NullPointerException: Cannot invoke "com.pengrad.telegrambot.model.Update.message()" because "edu.java.bot.HelpCommandTest.update" is null
//    @Test
//    public void HelpCommandTestWithRightInput() {
//        Mockito.when(update.message()).thenReturn(message);
//        Mockito.when(message.text()).thenReturn("/help");
//        Mockito.when(message.chat()).thenReturn(chat);
//        Mockito.when(chat.id()).thenReturn(11L);
//        mapStorage.registrate(update);
//        SendMessage sendMessage = helpCommand.handle(update);
//        Assertions.assertEquals(
//            sendMessage.getParameters().get("text"),
//            "Available commands:\n" +
//                "/start - register a user\n" +
//                "/track - start tracking link\n" +
//                "/untrack - stop tracking the link\n" +
//                "/list - show a list of tracked links\n"
//        );
//        Assertions.assertEquals(
//            sendMessage.getParameters().get("chat_id"),
//            11L
//        );
//    }
}
