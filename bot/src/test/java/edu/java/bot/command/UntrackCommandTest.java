package edu.java.bot.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.TestUtils;
import edu.java.bot.UpdateMock;
import edu.java.bot.service.command.UntrackCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class UntrackCommandTest extends LinksCommandsBaseTest {
    private UntrackCommand untrackCommand;
    private static final String UNTRACK_EMPTY_MSG = "You are not tracking this link yet.";
    private static final String DESCRIPTION = "Stop tracking a link";

    @BeforeEach
    public void setUp() {
        untrackCommand = new UntrackCommand(linkService);
    }

    @Test
    public void testEmpty() {
        Update update = new UpdateMock().withChat().withMessage("/untrack " + LINKS.get(0)).build();
        setUntracked(update.message().chat().id(), LINKS.get(0));
        SendMessage sendMessage = untrackCommand.process(update);
        TestUtils.checkMessage(sendMessage, UNTRACK_EMPTY_MSG);
    }

    @Test
    public void testOneMessage() {
        setAllSupported();
        Update update = new UpdateMock().withChat().withMessage("/untrack " + LINKS.get(0)).build();
        final long chatId = update.message().chat().id();
        setAllTracked(chatId, LINKS.get(0));
        SendMessage sendMessage = untrackCommand.process(update);
        TestUtils.checkMessage(sendMessage, generateUntrackedMessage(LINKS.get(0)));
    }

    @Test
    public void testTwoMessages() {
        setAllSupported();
        Update update1 = new UpdateMock().withChat().withMessage("/untrack " + LINKS.get(0)).build();
        final long chatId = update1.message().chat().id();
        setAllTracked(chatId, LINKS.get(0), LINKS.get(1));
        SendMessage sendMessage1 = untrackCommand.process(update1);
        TestUtils.checkMessage(sendMessage1, generateUntrackedMessage(LINKS.get(0)));
        setAllTracked(chatId, LINKS.get(1));

        Update update2 = new UpdateMock().withChat(chatId).withMessage("/untrack " + LINKS.get(1)).build();
        SendMessage sendMessage2 = untrackCommand.process(update2);
        TestUtils.checkMessage(sendMessage2, generateUntrackedMessage(LINKS.get(1)));
    }

    @Test
    public void testTwoSenders() {
        setAllSupported();
        Update update1 = new UpdateMock().withChat().withMessage("/untrack " + LINKS.get(0)).build();
        Update update2 = new UpdateMock().withChat().withMessage("/untrack " + LINKS.get(1)).build();
        final long chatId1 = update1.message().chat().id();
        final long chatId2 = update2.message().chat().id();
        setAllTracked(chatId1, LINKS.get(0));
        setAllTracked(chatId2, LINKS.get(1));
        SendMessage sendMessage1 = untrackCommand.process(update1);
        SendMessage sendMessage2 = untrackCommand.process(update2);
        TestUtils.checkMessage(sendMessage1, generateUntrackedMessage(LINKS.get(0)));
        TestUtils.checkMessage(sendMessage2, generateUntrackedMessage(LINKS.get(1)));
    }

    @Test
    public void testInvalid() {
        setAllSupported();
        Update update = new UpdateMock().withChat().withMessage("/untrack not-a-valid-link").build();
        final long chatId = update.message().chat().id();
        setAllUntracked(chatId);
        SendMessage sendMessage = untrackCommand.process(update);
        TestUtils.checkMessage(sendMessage, INVALID_LINK_MSG);
    }

    @Test
    public void testSupports() {
        assertThat(untrackCommand.supports(new UpdateMock().withMessage("/untrack " + LINKS.get(0))
            .build())).isTrue();
        assertThat(untrackCommand.supports(new UpdateMock().withMessage("/track " + LINKS.get(0))
            .build())).isFalse();
    }

    @Test
    public void testCommandAndDescription() {
        assertThat(untrackCommand.command()).isNotNull().isEqualTo("untrack");
        assertThat(untrackCommand.description()).isNotNull().isEqualTo(DESCRIPTION);
    }

    @Test
    public void testToApiCommand() {
        assertThat(untrackCommand.toApiCommand()).isNotNull().isEqualTo(new BotCommand("untrack", DESCRIPTION));
    }

    private String generateUntrackedMessage(String link) {
        return String.format("Link %s is no longer being tracked.", link);
    }
}
