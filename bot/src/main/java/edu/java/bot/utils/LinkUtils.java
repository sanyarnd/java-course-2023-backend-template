package edu.java.bot.utils;

import java.net.URI;
import edu.java.bot.model.Link;

public class LinkUtils {
    private LinkUtils() {}

    public static Link convertUriToLink(URI uri) {
        return new Link(uri.getHost(), uri.getPath(), uri.getQuery());
    }
}
