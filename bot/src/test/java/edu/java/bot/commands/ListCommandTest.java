package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.utils.LinkStorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class ListCommandTest {

    @Mock
    private LinkStorageService linkStorageService;

    @Mock
    private Update update;

    @Mock
    private Message message;

    @Mock
    private User user;

    @Mock
    private Chat chat;

    private ListCommand listCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(update.message()).thenReturn(message);
        when(message.from()).thenReturn(user);
        when(user.id()).thenReturn(123L);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);

        listCommand = new ListCommand(linkStorageService);
    }

    @Test
    void testEmptyListMessage() {
        when(linkStorageService.getLinks(anyLong())).thenReturn(Collections.emptySet());

        SendMessage response = listCommand.handle(update);

        Assertions.assertEquals(
            "На данный момент вы не отслеживаете никакие ссылки.",
            response.getParameters().get("text")
        );
    }

    @Test
    void testSingleLinkMessage() {
        Set<String> links = new HashSet<>();
        links.add("https://example.com");
        when(linkStorageService.getLinks(anyLong())).thenReturn(links);

        SendMessage response = listCommand.handle(update);

        Assertions.assertEquals(
            "Ваши отслеживаемые ссылки:\nhttps://example.com",
            response.getParameters().get("text")
        );
    }

    @Test
    void testMultipleLinksMessage() {
        Set<String> links = new HashSet<>();
        links.add("https://example.com");
        links.add("https://example.org");
        when(linkStorageService.getLinks(anyLong())).thenReturn(links);

        SendMessage response = listCommand.handle(update);

        Assertions.assertEquals(
            "Ваши отслеживаемые ссылки:\nhttps://example.com\nhttps://example.org",
            response.getParameters().get("text")
        );
    }
}
