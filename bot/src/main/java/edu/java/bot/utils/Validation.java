package edu.java.bot.utils;

import java.net.MalformedURLException;
import java.net.URI;

public class Validation {
    private Validation() {
    }

    public static boolean isLink(String text) {
        try {
            //noinspection ResultOfMethodCallIgnored
            URI.create(text).toURL();
            return true;
        } catch (IllegalArgumentException | MalformedURLException e) {
            return false;
        }
    }
}
