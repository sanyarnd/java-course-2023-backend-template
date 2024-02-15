package edu.java.bot.util;

import java.net.URI;

public interface LinkValidator {
    URI validateLinkAndGetURI(String url);
}
