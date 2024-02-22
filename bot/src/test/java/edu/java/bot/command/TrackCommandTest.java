package edu.java.bot.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.TestUtils;
import edu.java.bot.UpdateMock;
import edu.java.bot.service.command.TrackCommand;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TrackCommandTest extends LinksCommandsBaseTest {
    private TrackCommand trackCommand;
    private static final String DESCRIPTION = "Start tracking a link";
    private static final String TRACK_TWICE_MSG = "You are already tracking this link.";

    @BeforeEach
    public void setUp() {
        trackCommand = new TrackCommand(linkService);
    }

    @Test
    public void testOneMessage() {
        setAllSupported();
        Update update = new UpdateMock().withChat().withMessage("/track " + LINKS.get(0)).build();
        setUntracked(update.message().chat().id(), LINKS.get(0));
        SendMessage sendMessage = trackCommand.process(update);
        TestUtils.checkMessage(sendMessage, generateTrackStartMsg(LINKS.get(0)));
    }

    @Test
    public void testTwoMessages() {
        setAllSupported();
        Update update1 = new UpdateMock().withChat().withMessage("/track " + LINKS.get(0)).build();
        final long chatId = update1.message().chat().id();
        setAllUntracked(chatId);
        SendMessage sendMessage1 = trackCommand.process(update1);
        TestUtils.checkMessage(sendMessage1, generateTrackStartMsg(LINKS.get(0)));
        setTracked(chatId, LINKS.get(0));

        Update update2 = new UpdateMock().withChat(chatId).withMessage("/track " + LINKS.get(1)).build();
        SendMessage sendMessage2 = trackCommand.process(update2);
        TestUtils.checkMessage(sendMessage2, generateTrackStartMsg(LINKS.get(1)));
    }

    @Test
    public void testMessageTwice() {
        setAllSupported();
        Update update1 = new UpdateMock().withChat().withMessage("/track " + LINKS.get(0)).build();
        final long chatId = update1.message().chat().id();
        setAllUntracked(chatId);
        SendMessage sendMessage1 = trackCommand.process(update1);
        TestUtils.checkMessage(sendMessage1, generateTrackStartMsg(LINKS.get(0)));
        setTracked(chatId, LINKS.get(0));

        Update update2 = new UpdateMock().withChat(chatId).withMessage("/track " + LINKS.get(0)).build();
        SendMessage sendMessage2 = trackCommand.process(update2);
        TestUtils.checkMessage(sendMessage2, TRACK_TWICE_MSG);
    }

    @Test
    public void testTwoSenders() {
        setAllSupported();
        Update update1 = new UpdateMock().withChat().withMessage("/track " + LINKS.get(0)).build();
        Update update2 = new UpdateMock().withChat().withMessage("/track " + LINKS.get(1)).build();
        final long chatId1 = update1.message().chat().id();
        final long chatId2 = update2.message().chat().id();
        setAllUntracked(chatId1);
        setAllUntracked(chatId2);
        SendMessage sendMessage1 = trackCommand.process(update1);
        SendMessage sendMessage2 = trackCommand.process(update2);
        TestUtils.checkMessage(sendMessage1, generateTrackStartMsg(LINKS.get(0)));
        TestUtils.checkMessage(sendMessage2, generateTrackStartMsg(LINKS.get(1)));
    }

    @Test
    public void testUnsupported() {
        setSupported("github.com", "miro.com");
        Update update1 = new UpdateMock().withChat().withMessage("/track " + LINKS.get(0)).build();
        final long chatId = update1.message().chat().id();
        setAllUntracked(chatId);
        SendMessage sendMessage1 = trackCommand.process(update1);
        TestUtils.checkMessage(sendMessage1, generateUnsupportedMessage("github.com", "miro.com"));
    }

    @Test
    public void testInvalid() {
        setAllSupported();
        Update update = new UpdateMock().withChat().withMessage("/track not-a-valid-link").build();
        final long chatId = update.message().chat().id();
        setAllUntracked(chatId);
        SendMessage sendMessage = trackCommand.process(update);
        TestUtils.checkMessage(sendMessage, INVALID_LINK_MSG);
    }

    @Test
    public void testSupports() {
        assertThat(trackCommand.supports(new UpdateMock().withMessage("/track " + LINKS.get(0))
            .build())).isTrue();
        assertThat(trackCommand.supports(new UpdateMock().withMessage("/untrack " + LINKS.get(0))
            .build())).isFalse();
    }

    @Test
    public void testCommandAndDescription() {
        assertThat(trackCommand.command()).isNotNull().isEqualTo("track");
        assertThat(trackCommand.description()).isNotNull().isEqualTo(DESCRIPTION);
    }

    @Test
    public void testToApiCommand() {
        assertThat(trackCommand.toApiCommand()).isNotNull().isEqualTo(new BotCommand("track", DESCRIPTION));
    }

    private String generateTrackStartMsg(String link) {
        return String.format("Link %s is now being tracked.", link);
    }

    private String generateUnsupportedMessage(String... supportedDomains) {
        return "This domain is not supported yet. List of all supported domains:\n" +
            IntStream.range(0, supportedDomains.length).mapToObj(i -> (i + 1) + ". " + supportedDomains[i]).collect(
                Collectors.joining("\n"));
    }
}
