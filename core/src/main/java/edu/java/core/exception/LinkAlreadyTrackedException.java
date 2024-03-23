package edu.java.core.exception;

public class LinkAlreadyTrackedException extends InfoException {
    private final static String TEMPLATE = "Link [%d] for user [%d] already tracked!";

    public LinkAlreadyTrackedException(Long linkId, Long chatId) {
        super(String.format(TEMPLATE, linkId, chatId));
    }
}
