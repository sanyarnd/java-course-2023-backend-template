package edu.java.bot.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.net.URI;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class StackOverflowParserTest extends LinkParserTest {
    private StackOverflowParser stackOverflowParser;

    private static final URI FIRST_STACK_OVERFLOW_URI =
        URI.create("https://stackoverflow.com/questions/26318569/unfinished-stubbing-detected-in-mockito");
    private static final URI SECOND_STACK_OVERFLOW_URI =
        URI.create("https://stackoverflow.com/questions/66696828/how-to-use-configurationproperties-with-records");

    private static final URI FIRST_RANDOM_URI = URI.create("https://fintech.tinkoff.ru/study/");
    private static final URI SECOND_RANDOM_URI = URI.create("https://github.com/tonsky/FiraCode");

    @BeforeEach
    public void setUp() {
        stackOverflowParser = new StackOverflowParser();
    }

    @Test
    @Override
    public void testWithSupportedWebserviceLink() {
        assertTrue(stackOverflowParser.parseLink(FIRST_STACK_OVERFLOW_URI));
        assertTrue(stackOverflowParser.parseLink(SECOND_STACK_OVERFLOW_URI));
    }

    @Test
    @Override
    public void testWithUnsupportedWebserviceLink() {
        assertFalse(stackOverflowParser.parseLink(FIRST_RANDOM_URI));
        assertFalse(stackOverflowParser.parseLink(SECOND_RANDOM_URI));
    }
}
