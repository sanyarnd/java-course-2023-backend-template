package edu.java.bot.client.exception.handle;

import edu.java.bot.client.exception.ChatNotFoundException;
import edu.java.bot.client.exception.LinkAlreadyAddedException;
import edu.java.bot.client.exception.ScrapperClientException;
import edu.java.bot.client.exception.UserAlreadyRegisteredException;
import edu.java.bot.response.ApiErrorResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionGlobalHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> notValidArgumentHandle(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(BAD_REQUEST)
            .body(createError(ex, BAD_REQUEST.getReasonPhrase(), String.valueOf(BAD_REQUEST)));
    }

    @ExceptionHandler(ChatNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> chatNotFound(ChatNotFoundException ex) {
        return ResponseEntity.status(NOT_FOUND)
            .body(createError(ex, NOT_FOUND.getReasonPhrase(), String.valueOf(NOT_FOUND)));
    }

    @ExceptionHandler(LinkAlreadyAddedException.class)
    public ResponseEntity<ApiErrorResponse> linkAlreadyAdded(LinkAlreadyAddedException ex) {
        return ResponseEntity.status(CONFLICT)
            .body(createError(ex, CONFLICT.getReasonPhrase(), String.valueOf(CONFLICT)));
    }

    @ExceptionHandler(ScrapperClientException.class)
    public ResponseEntity<ApiErrorResponse> scrapperClientError(ScrapperClientException ex) {
        return ResponseEntity.status(BAD_REQUEST)
            .body(createError(ex, BAD_REQUEST.getReasonPhrase(), String.valueOf(BAD_REQUEST)));
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<ApiErrorResponse> userAlreadyRegistered(UserAlreadyRegisteredException ex) {
        return ResponseEntity.status(CONFLICT)
            .body(createError(ex, CONFLICT.getReasonPhrase(), String.valueOf(CONFLICT)));
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
