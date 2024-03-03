package edu.java.controller.exception;

public interface IAPIError {
    String getDescription();

    String getCode();

    String getName();

    String getMessage();

    StackTraceElement[] getStackTrace();
}
