package edu.java.scrapper.view.impl;

import edu.java.core.exception.InfoException;
import edu.java.core.exception.LinkAlreadyTrackedException;
import edu.java.core.exception.LinkCannotBeHandledException;
import edu.java.core.exception.LinkIsNotRegisteredException;
import edu.java.core.exception.LinkIsNotTrackedException;
import edu.java.core.exception.UserAlreadyAuthorizedException;
import edu.java.core.exception.UserIsNotAuthorizedException;
import edu.java.core.response.ApiErrorResponse;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(value = {
            LinkAlreadyTrackedException.class,
            LinkCannotBeHandledException.class,
            LinkIsNotRegisteredException.class,
            UserAlreadyAuthorizedException.class
    })
    public ResponseEntity<ApiErrorResponse> handle400(InfoException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(map(exception, HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(value = {
            LinkIsNotTrackedException.class,
            UserIsNotAuthorizedException.class
    })
    public ResponseEntity<ApiErrorResponse> handle404(InfoException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(map(exception, HttpStatus.NOT_FOUND.value()));
    }

    private ApiErrorResponse map(InfoException exception, Integer code) {
        return new ApiErrorResponse(
                exception.description,
                code,
                exception.getClass().getName(),
                exception.getMessage(),
                Arrays.stream(exception.getStackTrace()).map(String::valueOf).toList()
        );
    }
}
