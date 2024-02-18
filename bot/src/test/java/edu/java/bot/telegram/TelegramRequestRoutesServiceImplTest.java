package edu.java.bot.telegram;

import edu.java.bot.model.Link;
import edu.java.bot.model.UserMessage;
import edu.java.bot.repositories.UserLinksRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TelegramRequestRoutesServiceImplTest {
    private UserLinksRepository userLinksRepository;
    private TelegramRequestRoutesService telegramRequestRoutesService;

    @BeforeEach
    void setUp() {
        userLinksRepository = mock(UserLinksRepository.class);
        telegramRequestRoutesService = new TelegramRequestRoutesServiceImpl(userLinksRepository);
    }

    @Test
    void start() {
        var answer = telegramRequestRoutesService.start(new UserMessage("/start", 0L)).text();
        Assertions.assertTrue(answer.isPresent());
        Assertions.assertEquals("User registered", answer.get());
    }

    @Test
    void help() {
        var answer = telegramRequestRoutesService.help(new UserMessage("/help", 0L)).text();
        Assertions.assertTrue(answer.isPresent());
        Assertions.assertEquals("""
            Bot for notify about updates onto your favorite websites.
            Commands:
            /start -- user registration
            /help -- help message
            /track {link} -- start of tracking link for you
            /untrack {link} -- remove link from your tracking list
            /list -- list of your tracking links
            """, answer.get());
    }

    @Test
    void track_incorrectLink() {
        var answer = telegramRequestRoutesService.track(new UserMessage("/track https://", 0L)).text();
        Assertions.assertTrue(answer.isPresent());
        Assertions.assertTrue(answer.get().startsWith("Sended link is incorrect"));
    }

    @Test
    void track_correctPath() {
        when(userLinksRepository.addUserLinks(eq(0L), any())).thenReturn(true);
        var text = "/track https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c";
        var answer = telegramRequestRoutesService.track(new UserMessage(text, 0L)).text();
        Assertions.assertTrue(answer.isPresent());
        Assertions.assertEquals(answer.get(), "Link tracking started");
    }

    @Test
    void track_doubleLink() {
        when(userLinksRepository.addUserLinks(eq(0L), any())).thenReturn(true);
        var text = "/track https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c";
        var answer = telegramRequestRoutesService.track(new UserMessage(text, 0L)).text();
        Assertions.assertTrue(answer.isPresent());
        Assertions.assertEquals(answer.get(), "Link tracking started");

        when(userLinksRepository.addUserLinks(eq(0L), any())).thenReturn(false);
        text = "/track https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c";
        answer = telegramRequestRoutesService.track(new UserMessage(text, 0L)).text();
        Assertions.assertTrue(answer.isPresent());
        Assertions.assertEquals(answer.get(), "Link already tracking");
    }

    @Test
    void untrack_incorrectLink() {
        var answer = telegramRequestRoutesService.untrack(new UserMessage("/untrack https://", 0L)).text();
        Assertions.assertTrue(answer.isPresent());
        Assertions.assertTrue(answer.get().startsWith("Sended link is incorrect"));
    }

    @Test
    void untrack_correctPath() {
        when(userLinksRepository.removeUserLinks(eq(0L), any())).thenReturn(true);
        var text = "/track https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c";
        var answer = telegramRequestRoutesService.untrack(new UserMessage(text, 0L)).text();
        Assertions.assertTrue(answer.isPresent());
        Assertions.assertEquals(answer.get(), "Link tracking stopped");
    }

    @Test
    void untrack_doubleLink() {
        when(userLinksRepository.removeUserLinks(eq(0L), any())).thenReturn(true);
        var text = "/track https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c";
        var answer = telegramRequestRoutesService.untrack(new UserMessage(text, 0L)).text();
        Assertions.assertTrue(answer.isPresent());
        Assertions.assertEquals(answer.get(), "Link tracking stopped");

        when(userLinksRepository.removeUserLinks(eq(0L), any())).thenReturn(false);
        text = "/track https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c";
        answer = telegramRequestRoutesService.untrack(new UserMessage(text, 0L)).text();
        Assertions.assertTrue(answer.isPresent());
        Assertions.assertEquals(answer.get(), "Link isn't tracking");
    }

    @Test
    void list_empty() {
        when(userLinksRepository.getLinksByUser(eq(0L))).thenReturn(List.of());
        var text = "/list";
        var answer = telegramRequestRoutesService.list(new UserMessage(text, 0L)).text();
        Assertions.assertTrue(answer.isPresent());
        Assertions.assertEquals(answer.get(), "No tracking links.");
    }

    @Test
    void list_twoLinks() {
        var link1 = new Link("github.com", "/abacaba", null);
        var link2 = new Link("stackoverflow.com", "/barabarabara", "?berebere=bere");
        when(userLinksRepository.getLinksByUser(eq(0L))).thenReturn(List.of(link1, link2));
        var text = "/list";
        var answer = telegramRequestRoutesService.list(new UserMessage(text, 0L)).text();
        Assertions.assertTrue(answer.isPresent());
        Assertions.assertEquals(answer.get(), """
            Tracking links:
            - github.com/abacaba
            - stackoverflow.com/barabarabara?berebere=bere""");
    }
}
