package edu.java.controller.exception;

import java.util.List;

public interface IAPIError {
    String getDescription();
    String getCode();
    String getName();
    String getMessage();
    StackTraceElement[] getStackTrace();
}
