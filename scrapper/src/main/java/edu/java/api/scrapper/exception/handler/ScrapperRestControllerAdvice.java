package edu.java.api.scrapper.exception.handler;

import edu.java.api.scrapper.dto.response.ApiErrorResponse;
import edu.java.api.scrapper.exception.ChatNotFoundException;
import edu.java.api.scrapper.exception.LinkAlreadyAddedException;
import edu.java.api.scrapper.exception.ScrapperClientException;
import edu.java.api.scrapper.exception.UserAlreadyRegisteredException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ScrapperRestControllerAdvice {
    @ExceptionHandler(ChatNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> chatNotFoundHandle(Exception ex) {
        return ResponseEntity.status(NOT_FOUND)
            .body(createError(
                ex,
                NOT_FOUND.getReasonPhrase(),
                String.valueOf(NOT_FOUND.value())
            ));
    }

    @ExceptionHandler(LinkAlreadyAddedException.class)
    public ResponseEntity<ApiErrorResponse> linkAlreadyAddedHandler(Exception ex) {
        return ResponseEntity.status(CONFLICT)
            .body(createError(ex, CONFLICT.getReasonPhrase(), String.valueOf(CONFLICT.value())));
    }

    @ExceptionHandler(ScrapperClientException.class)
    public ResponseEntity<ApiErrorResponse> exceptionHandle(Exception ex) {
        return ResponseEntity.status(BAD_REQUEST)
            .body(createError(
                ex,
                BAD_REQUEST.getReasonPhrase(),
                String.valueOf(BAD_REQUEST.value())
            ));
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<ApiErrorResponse> userAlreadyRegisteredHandle(Exception ex) {
        return ResponseEntity.status(CONFLICT)
            .body(createError(ex, CONFLICT.getReasonPhrase(), String.valueOf(CONFLICT.value())));
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
