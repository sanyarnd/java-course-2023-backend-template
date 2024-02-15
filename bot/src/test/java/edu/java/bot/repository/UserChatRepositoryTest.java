package edu.java.bot.repository;

import edu.java.bot.model.UserChat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserChatRepositoryTest {
    private UserChatRepository userChatRepository;

    private UserChat firstUserChat;

    private UserChat secondUserChat;

    private List<String> links;

    private String testLink = "https://github.com/pengrad";

    @BeforeEach
    public void setUp() {
        userChatRepository = new UserChatRepository();

        links = new ArrayList<>(List.of(
            "https://github.com/pengrad/java-telegram-bot-api",
            "https://stackoverflow.com/questions/66696828/how-to-use-configurationproperties-with-records"
        ));
        firstUserChat = new UserChat(333L, new ArrayList<>());
        secondUserChat = new UserChat(777L, links);
    }

    @Test
    public void testUserRegistration() {
        userChatRepository.register(firstUserChat);
        assertEquals(firstUserChat, userChatRepository.findChat(firstUserChat.getChatId()));
    }

    @Test
    public void testGettingUserLinks() {
        userChatRepository.register(secondUserChat);
        assertEquals(links, userChatRepository.getUserLinks(secondUserChat.getChatId()));

        userChatRepository.register(firstUserChat);
        assertEquals(Collections.emptyList(), userChatRepository.getUserLinks(firstUserChat.getChatId()));
    }

    @Test
    public void testContainsLink() {
        userChatRepository.register(secondUserChat);
        assertTrue(userChatRepository.containsLink(secondUserChat.getChatId(), links.get(0)));
        assertFalse(userChatRepository.containsLink(secondUserChat.getChatId(), testLink));
    }

    @Test
    public void testAddingLink() {
        userChatRepository.register(firstUserChat);

        userChatRepository.addLink(firstUserChat.getChatId(), testLink);
        assertTrue(userChatRepository.containsLink(firstUserChat.getChatId(), testLink));
    }

    @Test
    public void testRemovalLink() {
        userChatRepository.register(secondUserChat);
        String removingLink = links.get(1);

        userChatRepository.removeLink(secondUserChat.getChatId(), removingLink);
        assertFalse(userChatRepository.containsLink(secondUserChat.getChatId(), removingLink));
    }
}
