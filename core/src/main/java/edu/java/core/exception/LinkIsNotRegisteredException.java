package edu.java.core.exception;

public class LinkIsNotRegisteredException extends InfoException {
    private final static String TEMPLATE = "Link with url [%s] is not in database!";

    public LinkIsNotRegisteredException(String url) {
        super(String.format(TEMPLATE, url));
    }
}
