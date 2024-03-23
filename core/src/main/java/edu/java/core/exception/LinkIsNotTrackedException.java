package edu.java.core.exception;

public class LinkIsNotTrackedException extends InfoException {
    private final static String TEMPLATE = "Link [%s] for user [%d] is not tracked!";

    public LinkIsNotTrackedException(String url, Long chatId) {
        super(String.format(TEMPLATE, url, chatId));
    }
}
