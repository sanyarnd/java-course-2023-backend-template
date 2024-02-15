package edu.java.bot.service;

import edu.java.bot.parser.GitHubParser;
import edu.java.bot.parser.LinkParser;
import edu.java.bot.parser.StackOverflowParser;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class LinkParsingProcessorTest {
    private LinkParsingProcessor linkParsingProcessor;

    private GitHubParser gitHubParser;

    private StackOverflowParser stackOverflowParser;

    private static final URI GIT_HUB_URI = URI.create("https://github.com/Soneech");
    private static final URI STACK_OVERFLOW_URI =
        URI.create("https://stackoverflow.com/questions/26318569/unfinished-stubbing-detected-in-mockito");
    private static final URI NOT_SUPPORTED_URI = URI.create("https://www.baeldung.com/mockito-junit-5-extension");

    @BeforeEach
    public void setUp() {
        gitHubParser = mock(GitHubParser.class);
        stackOverflowParser = mock(StackOverflowParser.class);

        List<LinkParser> parsers = new ArrayList<>(List.of(gitHubParser, stackOverflowParser));
        linkParsingProcessor = new LinkParsingProcessor(parsers);
    }

    @Test
    public void testParsingWithGitHubAndStackOverflowParsers() {
        doReturn(true).when(gitHubParser).parseLink(GIT_HUB_URI);
        doReturn(true).when(stackOverflowParser).parseLink(STACK_OVERFLOW_URI);

        assertTrue(linkParsingProcessor.processParsing(GIT_HUB_URI));
        assertTrue(linkParsingProcessor.processParsing(STACK_OVERFLOW_URI));
        assertFalse(linkParsingProcessor.processParsing(NOT_SUPPORTED_URI));
    }
}
