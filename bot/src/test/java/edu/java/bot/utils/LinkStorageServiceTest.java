package edu.java.bot.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LinkStorageServiceTest {

    private LinkStorageService linkStorageService;

    @BeforeEach
    void setUp() {
        linkStorageService = new LinkStorageService();
    }

    @Test
    void testAddLink() {
        Long userId = 1L;
        String link = "https://example.com";
        linkStorageService.addLink(userId, link);

        assertTrue(linkStorageService.getLinks(userId).contains(link));
    }

    @Test
    void testGetLinksEmptyByDefault() {
        Long userId = 2L;
        assertTrue(linkStorageService.getLinks(userId).isEmpty());
    }

    @Test
    void testRemoveLink() {
        Long userId = 3L;
        String link = "https://example.com";
        linkStorageService.addLink(userId, link);

        assertTrue(linkStorageService.removeLink(userId, link));
        Assertions.assertFalse(linkStorageService.getLinks(userId).contains(link));
    }

    @Test
    void testRemoveLinkNotExists() {
        Long userId = 4L;
        String link = "https://example.com";

        Assertions.assertFalse(linkStorageService.removeLink(userId, link));
    }

    @Test
    void testRemoveAllLinks() {
        Long userId = 5L;
        linkStorageService.addLink(userId, "https://example.com");
        linkStorageService.addLink(userId, "https://example.org");

        linkStorageService.removeAllLinks(userId);
        assertTrue(linkStorageService.getLinks(userId).isEmpty());
    }
}
