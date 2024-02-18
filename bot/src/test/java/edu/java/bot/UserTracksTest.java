package edu.java.bot;

import edu.java.bot.data.UsersTracks;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserTracksTest {

    @Test
    @DisplayName("addUserTest")
    public void testAddUser_NewUser_ReturnsTrue() {
        UsersTracks usersTracks = new UsersTracks();
        Long chatId = 42L;
        assertTrue(usersTracks.addUser(chatId));
        assertNotNull(usersTracks.getTrackedURLs(chatId));
        assertTrue(usersTracks.getTrackedURLs(chatId).isEmpty());
        //Testing idempotence

        assertFalse(usersTracks.addUser(chatId));
        assertNotNull(usersTracks.getTrackedURLs(chatId));
        assertTrue(usersTracks.getTrackedURLs(chatId).isEmpty());
    }

    @Test
    @DisplayName("getTrackedURLsTest")
    public void testAddUser_ExistingUser_ReturnsFalse() {
        UsersTracks usersTracks = new UsersTracks();
        Long chatId = 42L;
        usersTracks.addUser(chatId);
        usersTracks.addTrackedURLs(chatId, "https://www.google.com");
        usersTracks.addTrackedURLs(chatId, "https://docs.github.com/en/authentication/connecting-to-github-with-ssh");
        usersTracks.addTrackedURLs(
            chatId,
            "https://docs.github.com/ru/authentication/troubleshooting-ssh/error-permission-denied-publickey"
        );
        HashSet<String> testHashSet = new HashSet<>();
        testHashSet.add("https://www.google.com");
        testHashSet.add("https://docs.github.com/en/authentication/connecting-to-github-with-ssh");
        testHashSet.add(
            "https://docs.github.com/ru/authentication/troubleshooting-ssh/error-permission-denied-publickey");
        assertEquals(usersTracks.getTrackedURLs(chatId), testHashSet);

        usersTracks.removeTrackedURLs(
            chatId,
            "https://docs.github.com/en/authentication/connecting-to-github-with-ssh"
        );
        testHashSet.remove("https://docs.github.com/en/authentication/connecting-to-github-with-ssh");
        assertEquals(usersTracks.getTrackedURLs(chatId), testHashSet);
    }

    @Test
    @DisplayName("addTrackedURLsTest")
    public void testCheckURL_ValidURL_ReturnsTrue() {
        UsersTracks usersTracks = new UsersTracks();
        Long chatId = 42L;
        usersTracks.addUser(chatId);
        assertFalse(usersTracks.addTrackedURLs(chatId, "mouse"));
        assertFalse(usersTracks.addTrackedURLs(chatId, "1"));
        assertFalse(usersTracks.addTrackedURLs(chatId, "chicken.org"));

        assertTrue(usersTracks.addTrackedURLs(chatId, "https://www.google.com"));
        assertTrue(usersTracks.addTrackedURLs(
            chatId,
            "https://docs.github.com/en/authentication/connecting-to-github-with-ssh"
        ));
        assertTrue(usersTracks.addTrackedURLs(
            chatId,
            "https://docs.github.com/en/authentication/connecting-to-github-with-ssh"
        ));
        assertTrue(usersTracks.addTrackedURLs(
            chatId,
            "https://docs.github.com/ru/authentication/troubleshooting-ssh/error-permission-denied-publickey"
        ));
        assertTrue(usersTracks.addTrackedURLs(
            chatId,
            "https://docs.github.com/ru/authentication/troubleshooting-ssh/error-permission-denied-publickey"
        ));
        HashSet<String> testHashSet = new HashSet<>();
        testHashSet.add("https://www.google.com");
        testHashSet.add("https://docs.github.com/en/authentication/connecting-to-github-with-ssh");
        testHashSet.add(
            "https://docs.github.com/ru/authentication/troubleshooting-ssh/error-permission-denied-publickey");
        assertEquals(usersTracks.getTrackedURLs(chatId), testHashSet);
    }

    @Test
    @DisplayName("removeTrackedURLsTest")
    public void testCheckURL_InvalidURL_ReturnsFalse() {
        UsersTracks usersTracks = new UsersTracks();
        Long chatId = 42L;
        usersTracks.addUser(chatId);
        usersTracks.addTrackedURLs(chatId, "https://www.google.com");
        usersTracks.addTrackedURLs(chatId, "https://docs.github.com/en/authentication/connecting-to-github-with-ssh");
        usersTracks.addTrackedURLs(
            chatId,
            "https://docs.github.com/ru/authentication/troubleshooting-ssh/error-permission-denied-publickey"
        );
        HashSet<String> testHashSet = new HashSet<>();
        testHashSet.add("https://www.google.com");
        testHashSet.add("https://docs.github.com/en/authentication/connecting-to-github-with-ssh");
        testHashSet.add(
            "https://docs.github.com/ru/authentication/troubleshooting-ssh/error-permission-denied-publickey");
        assertEquals(usersTracks.getTrackedURLs(chatId), testHashSet);

        usersTracks.removeTrackedURLs(
            chatId,
            "https://docs.github.com/ru/authentication/troubleshooting-ssh/error-permission-denied-publickey"
        );
        testHashSet.remove(
            "https://docs.github.com/ru/authentication/troubleshooting-ssh/error-permission-denied-publickey");
        assertEquals(usersTracks.getTrackedURLs(chatId), testHashSet);

        usersTracks.removeTrackedURLs(
            chatId,
            "https://docs.github.com/en/authentication/connecting-to-github-with-ssh"
        );
        testHashSet.remove("https://docs.github.com/en/authentication/connecting-to-github-with-ssh");
        assertEquals(usersTracks.getTrackedURLs(chatId), testHashSet);

        usersTracks.removeTrackedURLs(chatId, "https://www.google.com");
        testHashSet.remove("https://www.google.com");
        assertEquals(usersTracks.getTrackedURLs(chatId), testHashSet);

    }

    //TODO:: ADD MORE TESTS
}
