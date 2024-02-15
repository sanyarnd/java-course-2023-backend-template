package edu.java.bot.util;

import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class LinkValidatorTest {
    private LinkValidator linkValidator;

    private static final String NOT_LINK = "eijfweifj";

    private static final String INVALID_LINK = "https://github.com/      Soneech";

    private static final String CORRECT_LINK = "https://github.com/Soneech";

    @BeforeEach
    public void setUp() {
        linkValidator = new LinkValidatorImpl();
    }

    @Test
    public void testWithIncorrectLink() {
        assertNull(linkValidator.validateLinkAndGetURI(NOT_LINK));
        assertNull(linkValidator.validateLinkAndGetURI(INVALID_LINK));
    }

    @Test
    public void testWithCorrectLinks() {
        assertEquals(URI.create(CORRECT_LINK), linkValidator.validateLinkAndGetURI(CORRECT_LINK));
    }
}
