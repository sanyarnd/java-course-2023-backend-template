package edu.java.bot.api.controller.exception.handler;

import edu.java.bot.api.controller.dto.response.ApiErrorResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@org.springframework.web.bind.annotation.RestControllerAdvice
public class BotRestControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> notValidArgumentHandle(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(BAD_REQUEST)
            .body(createError(ex, BAD_REQUEST.getReasonPhrase(), String.valueOf(BAD_REQUEST)));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> exception(Exception ex) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
            .body(createError(ex, INTERNAL_SERVER_ERROR.getReasonPhrase(), String.valueOf(INTERNAL_SERVER_ERROR)));
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
