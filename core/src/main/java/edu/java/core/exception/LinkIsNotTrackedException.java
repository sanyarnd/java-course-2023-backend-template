package edu.java.core.exception;

public class LinkIsNotTrackedException extends InfoException {
    private final static String TEMPLATE = "Link with url [%s] is not tracked!";

    public LinkIsNotTrackedException(String url) {
        super(String.format(TEMPLATE, url));
    }
}
