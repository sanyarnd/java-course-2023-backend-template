package edu.java.bot.clients;

import edu.java.bot.clients.dto.ApiErrorResponse;
import java.util.List;
import lombok.Data;

@Data
public class ScrapperApiException extends RuntimeException {
    private final String description;
    private final String code;
    private final String exceptionName;
    private final String exceptionMessage;
    private final List<String> apiExceptionStackTrace;

    public ScrapperApiException(ApiErrorResponse apiErrorResponse) {
        this.description = apiErrorResponse.getDescription();
        this.code = apiErrorResponse.getCode();
        this.exceptionName = apiErrorResponse.getExceptionName();
        this.exceptionMessage = apiErrorResponse.getExceptionMessage();
        this.apiExceptionStackTrace = apiErrorResponse.getStackTrace();
    }
}
