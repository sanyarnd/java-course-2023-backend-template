package edu.java.core.exception;

public abstract class InfoException extends RuntimeException {
    public final String description;

    protected InfoException(String description) {
        this.description = description;
    }

    protected InfoException(String message, Throwable cause) {
        super(message, cause);
        this.description = message;
    }

    protected InfoException(String message, String description, Throwable cause) {
        super(message, cause);
        this.description = description;
    }
}
