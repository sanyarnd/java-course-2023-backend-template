package edu.java.bot.parser;

import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class GitHubParserTest extends LinkParserTest {
    private GitHubParser gitHubParser;

    private static final URI FIRST_GIT_HUB_URI = URI.create("https://github.com/pengrad/java-telegram-bot-api");

    private static final URI SECOND_GIT_HUB_URI = URI.create("https://github.com/tonsky/FiraCode");

    private static final URI FIRST_RANDOM_URI = URI.create("https://fintech.tinkoff.ru/study/");

    private static final URI SECOND_RANDOM_URI =
        URI.create("https://stackoverflow.com/questions/28295625/mockito-spy-vs-mock");

    @BeforeEach
    public void setUp() {
        gitHubParser = new GitHubParser();
    }

    @Test
    @Override
    public void testWithSupportedWebserviceLink() {
        assertTrue(gitHubParser.parseLink(FIRST_GIT_HUB_URI));
        assertTrue(gitHubParser.parseLink(SECOND_GIT_HUB_URI));
    }

    @Test
    @Override
    public void testWithUnsupportedWebserviceLink() {
        assertFalse(gitHubParser.parseLink(FIRST_RANDOM_URI));
        assertFalse(gitHubParser.parseLink(SECOND_RANDOM_URI));
    }
}
