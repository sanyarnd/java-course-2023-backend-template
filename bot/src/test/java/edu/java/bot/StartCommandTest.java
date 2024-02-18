package edu.java.bot;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import edu.java.bot.dao.MapStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class StartCommandTest {

    public static Update update;
    public static Message message;
    public static Chat chat;


    @Autowired
    Command startCommand;

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

    @Test
    void StartCommandTestWithRightInputAndRepeatedInput() {
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.text()).thenReturn("/start");
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(11L);
        SendMessage sendMessage1 = startCommand.handle(update);
        SendMessage sendMessage2 = startCommand.handle(update);

        Assertions.assertEquals(
            sendMessage1.getParameters().get("text"),
            "You have successfully registered"
        );
        Assertions.assertEquals(
            sendMessage1.getParameters().get("chat_id"),
            11L
        );

        Assertions.assertEquals(
            sendMessage2.getParameters().get("text"),
            "You are already registered"
        );
        Assertions.assertEquals(
            sendMessage1.getParameters().get("chat_id"),
            11L
        );
    }


    @Test
    void StartCommandTestWithWrongInput() {
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.text()).thenReturn("/star");
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(11L);
        SendMessage sendMessage1 = startCommand.handle(update);

        Assertions.assertEquals(
            sendMessage1.getParameters().get("text"),
            "Enter /start to register in the bot"
        );
        Assertions.assertEquals(
            sendMessage1.getParameters().get("chat_id"),
            11L
        );

    }

}
