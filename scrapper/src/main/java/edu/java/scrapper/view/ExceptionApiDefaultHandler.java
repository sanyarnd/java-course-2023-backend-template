package edu.java.scrapper.view;

import edu.java.core.exception.UnrecognizableException;
import edu.java.core.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionApiDefaultHandler {
    @ExceptionHandler(UnrecognizableException.class)
    public ResponseEntity<ApiErrorResponse> handleTrackingExceptions(UnrecognizableException exception) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(exception.getErrorResponse());
    }
}
