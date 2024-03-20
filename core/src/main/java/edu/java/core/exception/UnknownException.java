package edu.java.core.exception;

public class UnknownException extends InfoException {
    private final static String TEMPLATE = "Unknown exception in class [%s]!";

    public UnknownException(Class<?> exceptionSourceClazz, Throwable cause) {
        super(String.format(TEMPLATE, exceptionSourceClazz.getName()), "No description", cause);
    }
}
