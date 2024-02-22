package edu.java.bot.exception;

public class NoSuchChatException extends RuntimeException {
    public NoSuchChatException() {
    }

    public NoSuchChatException(String message) {
        super(message);
    }

    public NoSuchChatException(String message, Throwable cause) {
        super(message, cause);
    }
}
