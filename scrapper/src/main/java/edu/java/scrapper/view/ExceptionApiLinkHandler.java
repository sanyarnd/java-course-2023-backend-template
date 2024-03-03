package edu.java.scrapper.view;

import edu.java.core.exception.LinkAlreadyTracked;
import edu.java.core.exception.LinkIsUnreachable;
import edu.java.core.exception.LinkNotTracked;
import edu.java.core.exception.util.ExceptionSerializer;
import edu.java.core.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionApiLinkHandler {
    private final ExceptionSerializer exceptionSerializer;

    public ExceptionApiLinkHandler(ExceptionSerializer exceptionSerializer) {
        this.exceptionSerializer = exceptionSerializer;
    }

    @ExceptionHandler({LinkAlreadyTracked.class, LinkNotTracked.class})
    public ResponseEntity<ApiErrorResponse> handleTrackingExceptions(Exception exception) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(exceptionSerializer.serialize(exception, 400));
    }

    @ExceptionHandler(LinkIsUnreachable.class)
    public ResponseEntity<ApiErrorResponse> handleUnreachableException(LinkIsUnreachable exception) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(exceptionSerializer.serialize(exception, 404));
    }
}
