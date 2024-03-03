package edu.java.core.exception.util;

import edu.java.core.response.ApiErrorResponse;

public interface ExceptionSerializer {
    ApiErrorResponse serialize(Exception exception, Integer code);
}
