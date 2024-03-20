package edu.java.core.exception;

public class UserAlreadyAuthorizedException extends InfoException {
    private final static String TEMPLATE = "User with id [%d] already authorized!";

    public UserAlreadyAuthorizedException(Long chatId) {
        super(String.format(TEMPLATE, chatId));
    }
}
