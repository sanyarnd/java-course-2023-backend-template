package edu.java.core.exception;

public class LinkCannotBeHandledException extends InfoException {
    private final static String TEMPLATE = "Link with url [%s] cannot be handled!";

    public LinkCannotBeHandledException(String url) {
        super(String.format(TEMPLATE, url));
    }
}
