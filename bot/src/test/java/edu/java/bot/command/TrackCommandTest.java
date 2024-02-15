package edu.java.bot.command;

import edu.java.bot.service.LinkParsingProcessor;
import edu.java.bot.util.LinkValidator;
import edu.java.bot.website.WebsiteInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.net.URI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class TrackCommandTest extends CommandTest {
    @InjectMocks
    private TrackCommand trackCommand;

    @Mock
    private LinkValidator linkValidator;

    @Mock
    private LinkParsingProcessor linkParsingProcessor;

    private static final String GIT_HUB_LINK = "https://github.com/pengrad/java-telegram-bot-api";

    private static final String STACK_OVERFLOW_LINK =
        "https://stackoverflow.com/questions/28295625/mockito-spy-vs-mock";

    private static final String NOT_LINK = "qwertyuiop[519844sfw e";

    private static final String LINK_WITH_SPACES = "https://github.com   /Soneech";

    private static final String UNSUPPORTED_SERVICE_LINK = "https://www.youtube.com/@strannoemestechko";

    @Test
    @Override
    void testThatReturnedCommandTypeIsCorrect() {
        assertEquals(CommandInfo.TRACK.getType(), trackCommand.type());
    }

    @Test
    @Override
    void testThatReturnedCommandDescriptionIsCorrect() {
        assertEquals(CommandInfo.TRACK.getDescription(), trackCommand.description());
    }

    @Test
    public void testIncorrectCommandFormatMessage() {
        doReturn("/track").when(message).text();
        assertEquals("Неверный формат команды. /help",
            trackCommand.processCommand(update).getParameters().get("text"));
    }

    @Test
    public void testLinkAlreadyAddedMessage() {
        doReturn(true).when(userChatRepository).containsLink(chatId, GIT_HUB_LINK);
        doReturn("/track " + GIT_HUB_LINK).when(message).text();

        assertEquals("Данная ссылка уже добавлена. /list",
            trackCommand.processCommand(update).getParameters().get("text"));
    }

    @Test
    public void testIncorrectLinkMessage() {
        String botMessage = "Некорректная ссылка.";

        doReturn(null).when(linkValidator).validateLinkAndGetURI(LINK_WITH_SPACES);
        doReturn("/track " + LINK_WITH_SPACES).when(message).text();
        assertEquals(botMessage, trackCommand.processCommand(update).getParameters().get("text"));

        doReturn(null).when(linkValidator).validateLinkAndGetURI(NOT_LINK);
        doReturn("/track " + NOT_LINK).when(message).text();
        assertEquals(botMessage, trackCommand.processCommand(update).getParameters().get("text"));
    }

    @Test
    public void testThatCorrectNewGitHubLinkWasAdded() {
        URI uri = URI.create(GIT_HUB_LINK);

        doReturn(uri).when(linkValidator).validateLinkAndGetURI(GIT_HUB_LINK);
        doReturn(true).when(linkParsingProcessor).processParsing(uri);
        doReturn("/track " + GIT_HUB_LINK).when(message).text();

        assertEquals("Ссылка успешно добавлена к отслеживанию. /list",
            trackCommand.processCommand(update).getParameters().get("text"));
    }

    @Test
    public void testThatCorrectNewStackOverflowLinkWasAdded() {
        URI uri = URI.create(STACK_OVERFLOW_LINK);
        doReturn(uri).when(linkValidator).validateLinkAndGetURI(STACK_OVERFLOW_LINK);
        doReturn(true).when(linkParsingProcessor).processParsing(uri);
        doReturn("/track " + STACK_OVERFLOW_LINK).when(message).text();

        assertEquals("Ссылка успешно добавлена к отслеживанию. /list",
            trackCommand.processCommand(update).getParameters().get("text"));
    }

    @Test
    public void testUnsupportedWebservicesMessage() {
        StringBuilder botMessage = new StringBuilder();
        botMessage.append("Данный сервис не поддерживается :(\nПоддерживаемые сервисы приведены ниже:");
        for (var website: WebsiteInfo.values()) {
            botMessage.append("\n").append(website.getDomain());
        }

        URI firstURI = URI.create(UNSUPPORTED_SERVICE_LINK);
        doReturn(firstURI).when(linkValidator).validateLinkAndGetURI(UNSUPPORTED_SERVICE_LINK);
        doReturn(false).when(linkParsingProcessor).processParsing(firstURI);
        doReturn("/track " + UNSUPPORTED_SERVICE_LINK).when(message).text();

        assertEquals(botMessage.toString(), trackCommand.processCommand(update).getParameters().get("text"));
    }
}
