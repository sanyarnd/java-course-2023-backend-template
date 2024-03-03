package edu.java.clients;

import edu.java.clients.dto.ApiErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;

@Data
public class BotApiException extends RuntimeException {
    String description;
    String code;
    String exceptionName;
    String exceptionMessage;
    List<String> apiExceptionStackTrace;

    public BotApiException(ApiErrorResponse apiErrorResponse) {
        this.description = apiErrorResponse.getDescription();
        this.code = apiErrorResponse.getCode();
        this.exceptionName = apiErrorResponse.getExceptionName();
        this.exceptionMessage = apiErrorResponse.getExceptionMessage();
        this.apiExceptionStackTrace = apiErrorResponse.getStackTrace();
    }
}
