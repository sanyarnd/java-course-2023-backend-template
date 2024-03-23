package edu.java.core.exception;

public class LinkIsNotTrackedException extends InfoException {
    private final static String TEMPLATE = "Link [%d] for user [%d] is not tracked!";

    public LinkIsNotTrackedException(Long linkId, Long chatId) {
        super(String.format(TEMPLATE, linkId, chatId));
    }
}
