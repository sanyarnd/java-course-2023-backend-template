package edu.java.core.exception;

import edu.java.core.response.ApiErrorResponse;
import lombok.Getter;

@Getter
public class ApiErrorException extends InfoException {
    private final ApiErrorResponse apiErrorResponse;

    public ApiErrorException(ApiErrorResponse apiErrorResponse) {
        super(apiErrorResponse.description());
        this.apiErrorResponse = apiErrorResponse;
    }
}
