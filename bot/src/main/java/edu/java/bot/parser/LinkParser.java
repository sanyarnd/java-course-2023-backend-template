package edu.java.bot.parser;

import java.net.URI;

public interface LinkParser {
    boolean parseLink(URI uri);  // пока просто проверяет, что ссыль либо на github, либо на stackoverflow
}
