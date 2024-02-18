package edu.java.bot;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.dao.MapStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.net.URI;

@SpringBootTest
public class ListCommandTest {
    public static Update update;
    public static Message message;
    public static Chat chat;

    @Autowired
    public ListCommand listCommand;


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

    @Test
    void ListCommandTestWithRightInput() throws Exception {
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.text()).thenReturn("/list");
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(11L);

        SendMessage sendMessage1= listCommand.handle(update);
        mapStorage.registrate(update);
        SendMessage sendMessage2= listCommand.handle(update);
        mapStorage.addSubscription(update, URI.create("https://github.com/imponomarev"));
        SendMessage sendMessage3= listCommand.handle(update);

        Assertions.assertEquals(
            "you aren't logged in, type /start",
            sendMessage1.getParameters().get("text")
        );
        Assertions.assertEquals(
            sendMessage1.getParameters().get("chat_id"),
            11L
        );

        Assertions.assertEquals(
            "You aren't subscribed to anything",
            sendMessage2.getParameters().get("text")
        );
        Assertions.assertEquals(
            sendMessage2.getParameters().get("chat_id"),
            11L
        );

        Assertions.assertEquals(
            "your subscriptions:\nhttps://github.com/imponomarev\n",
            sendMessage3.getParameters().get("text")
        );
        Assertions.assertEquals(
            sendMessage3.getParameters().get("chat_id"),
            11L
        );
    }
}
