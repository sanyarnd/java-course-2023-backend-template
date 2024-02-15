package edu.java.bot.parser;

import org.junit.jupiter.api.Test;

public abstract class LinkParserTest {
    @Test
    public abstract void testWithSupportedWebserviceLink();

    @Test
    public abstract void testWithUnsupportedWebserviceLink();
}
