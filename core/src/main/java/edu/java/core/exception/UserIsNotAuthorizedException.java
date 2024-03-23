package edu.java.core.exception;

public class UserIsNotAuthorizedException extends InfoException {
    private final static String TEMPLATE = "User with id [%d] is not authorized!";

    public UserIsNotAuthorizedException(Long chatId) {
        super(String.format(TEMPLATE, chatId));
    }
}
