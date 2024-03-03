package edu.java.controller.exception;

import edu.java.controller.dto.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ScrapperControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handler(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrorResponse(ex));
    }

    @ExceptionHandler({CantHandleURLException.class, ChatNotFoundException.class, ChatReAddingException.class,
        LinkNotFoundException.class, LinkReAddingException.class})
    public ResponseEntity<ApiErrorResponse> handler(IAPIError ex) {
        return ResponseEntity.status(HttpStatus.valueOf(Integer.parseInt(ex.getCode()))).body(new ApiErrorResponse(ex));
    }
}
