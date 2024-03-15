package edu.java.client.exception.handle;

import edu.java.client.exception.BotClientException;
import edu.java.payload.dto.response.ApiErrorResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@org.springframework.web.bind.annotation.RestControllerAdvice
public class ExceptionGlobalHandler {
    @ExceptionHandler(BotClientException.class)
    public ResponseEntity<ApiErrorResponse> notValidArgumentHandle(BotClientException ex) {
        return ResponseEntity.status(BAD_REQUEST)
            .body(createError(ex, BAD_REQUEST.getReasonPhrase(), String.valueOf(BAD_REQUEST)));
    }

    private ApiErrorResponse createError(Throwable exception, String description, String code) {

        List<String> stackTrace = new ArrayList<>();
        for (StackTraceElement element : exception.getStackTrace()) {
            stackTrace.add(element.toString());
        }

        return new ApiErrorResponse(
            description,
            code,
            exception.getClass().getCanonicalName(),
            exception.getMessage(),
            stackTrace
        );
    }
}
