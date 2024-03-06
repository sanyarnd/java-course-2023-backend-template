package edu.java.scrapper.view.impl;

import edu.java.core.exception.util.ExceptionSerializer;
import edu.java.core.response.ApiErrorResponse;
import java.util.Arrays;
import org.springframework.stereotype.Component;

@Component
public class ExceptionSerializerImpl implements ExceptionSerializer {
    @Override
    public ApiErrorResponse serialize(Exception exception, Integer code) {
        return new ApiErrorResponse(
            exception.getMessage(),
            code.toString(),
            exception.getClass().getName(),
            exception.getCause().getMessage(),
            Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList()
        );
    }
}
