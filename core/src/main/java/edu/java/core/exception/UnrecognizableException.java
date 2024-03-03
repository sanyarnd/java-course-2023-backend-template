package edu.java.core.exception;

import edu.java.core.response.ApiErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UnrecognizableException extends RuntimeException {
    private final ApiErrorResponse errorResponse;
}
