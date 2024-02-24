package edu.java.bot.utils;

import edu.java.bot.model.Link;
import java.net.URI;

public class LinkUtils {
    private LinkUtils() {
    }

    public static Link convertUriToLink(URI uri) {
        return new Link(uri.getHost(), uri.getPath(), uri.getQuery());
    }
}
