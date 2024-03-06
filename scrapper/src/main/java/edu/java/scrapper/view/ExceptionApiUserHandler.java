package edu.java.scrapper.view;

import edu.java.core.exception.UserAlreadyAuthenticated;
import edu.java.core.exception.UserAlreadyRegistered;
import edu.java.core.exception.UserNotAuthenticated;
import edu.java.core.exception.UserNotRegistered;
import edu.java.core.exception.util.ExceptionSerializer;
import edu.java.core.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionApiUserHandler {
    private final ExceptionSerializer exceptionSerializer;

    public ExceptionApiUserHandler(ExceptionSerializer exceptionSerializer) {
        this.exceptionSerializer = exceptionSerializer;
    }

    private final static Integer BAD_REQUEST_CODE = 400;

    @ExceptionHandler({UserAlreadyAuthenticated.class, UserNotAuthenticated.class, UserAlreadyRegistered.class,
        UserNotRegistered.class})
    public ResponseEntity<ApiErrorResponse> handleUserExceptions(Exception exception) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(exceptionSerializer.serialize(exception, BAD_REQUEST_CODE));
    }
}
