package edu.java.core.exception.util;

import edu.java.core.response.ApiErrorResponse;

public interface ExceptionDeserializer {
    Exception deserialize(ApiErrorResponse response);
}
