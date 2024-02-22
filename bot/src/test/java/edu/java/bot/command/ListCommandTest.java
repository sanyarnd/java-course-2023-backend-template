package edu.java.bot.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.TestUtils;
import edu.java.bot.UpdateMock;
import edu.java.bot.service.command.ListCommand;
import edu.java.bot.util.CommonUtils;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class ListCommandTest extends LinksCommandsBaseTest {
    private ListCommand listCommand;
    private static final String LIST_EMPTY_MSG = "You don't have any tracked links.\nUse /track to start tracking.";
    private static final String DESCRIPTION = "Show all tracked links";

    @BeforeEach
    public void setUp() {
        listCommand = new ListCommand(linkService);
    }

    @Test
    public void testEmpty() {
        Update update1 = new UpdateMock().withChat().build();
        final long chatId = update1.message().chat().id();
        SendMessage sendMessage1 = listCommand.process(update1);
        TestUtils.checkMessage(sendMessage1, LIST_EMPTY_MSG);

        setAllTracked(chatId, LINKS.get(0), LINKS.get(1), LINKS.get(1));
        setUntracked(chatId, LINKS.get(0));
        listCommand.process(new UpdateMock().withChat(chatId).build());
        setTracked(chatId, LINKS.get(3));
        setAllUntracked(chatId);

        Update update2 = new UpdateMock().withChat(chatId).build();
        SendMessage sendMessage2 = listCommand.process(update2);
        TestUtils.checkMessage(sendMessage2, LIST_EMPTY_MSG);
    }

    @Test
    public void testOne() {
        final String[] links = {LINKS.get(0), LINKS.get(1), LINKS.get(2), LINKS.get(3)};
        final String expectedMessage = generateListMsg(links);

        Update update = new UpdateMock().withChat().build();
        final long chatId = update.message().chat().id();
        setAllTracked(chatId, links);
        SendMessage sendMessage = listCommand.process(update);
        TestUtils.checkMessage(sendMessage, expectedMessage);
    }

    @Test
    public void testTwo() {
        final String[] links = {LINKS.get(0), LINKS.get(1), LINKS.get(2), LINKS.get(3)};
        final String expectedMessage = generateListMsg(links);

        Update update1 = new UpdateMock().withChat().build();
        final long chatId = update1.message().chat().id();
        setAllTracked(chatId, links);
        SendMessage sendMessage1 = listCommand.process(update1);
        TestUtils.checkMessage(sendMessage1, expectedMessage);

        Update update2 = new UpdateMock().withChat(chatId).build();
        SendMessage sendMessage2 = listCommand.process(update2);
        TestUtils.checkMessage(sendMessage2, expectedMessage);
    }

    @Test
    public void testModified() {
        final String[] links1 = {LINKS.get(0), LINKS.get(1), LINKS.get(2), LINKS.get(3)};
        final String expectedMessage1 = generateListMsg(links1);

        Update update1 = new UpdateMock().withChat().build();
        final long chatId = update1.message().chat().id();
        setAllTracked(chatId, links1);
        SendMessage sendMessage1 = listCommand.process(update1);
        TestUtils.checkMessage(sendMessage1, expectedMessage1);

        final String[] links2 = {LINKS.get(4), LINKS.get(5), LINKS.get(6), LINKS.get(7), LINKS.get(8)};
        setAllTracked(chatId, links2);
        final String expectedMessage2 = generateListMsg(links2);

        Update update2 = new UpdateMock().withChat(chatId).build();
        SendMessage sendMessage2 = listCommand.process(update2);
        TestUtils.checkMessage(sendMessage2, expectedMessage2);
    }

    @Test
    public void testTwoSenders() {
        final String[] links1 = {LINKS.get(0), LINKS.get(1), LINKS.get(2), LINKS.get(3)};
        final String[] links2 = {LINKS.get(4), LINKS.get(5), LINKS.get(6), LINKS.get(7), LINKS.get(8)};
        final String expectedMessage1 = generateListMsg(links1);
        final String expectedMessage2 = generateListMsg(links2);

        Update update1 = new UpdateMock().withChat().build();
        Update update2 = new UpdateMock().withChat().build();
        final long chatId1 = update1.message().chat().id();
        final long chatId2 = update2.message().chat().id();
        setAllTracked(chatId1, links1);
        setAllTracked(chatId2, links2);

        SendMessage sendMessage1 = listCommand.process(update1);
        SendMessage sendMessage2 = listCommand.process(update2);
        TestUtils.checkMessage(sendMessage1, expectedMessage1);
        TestUtils.checkMessage(sendMessage2, expectedMessage2);
    }

    @Test
    public void testSupports() {
        assertThat(listCommand.supports(new UpdateMock().withMessage("/list").build())).isTrue();
        assertThat(listCommand.supports(new UpdateMock().withMessage("/track " + LINKS.get(0))
            .build())).isFalse();
    }

    @Test
    public void testCommandAndDescription() {
        assertThat(listCommand.command()).isNotNull().isEqualTo("list");
        assertThat(listCommand.description()).isNotNull().isEqualTo(DESCRIPTION);
    }

    @Test
    public void testToApiCommand() {
        assertThat(listCommand.toApiCommand()).isNotNull().isEqualTo(new BotCommand("list", DESCRIPTION));
    }

    private String generateListMsg(String... links) {
        return "Your tracked links:\n" + CommonUtils.joinEnumerated(Arrays.asList(links), 1);
    }
}
