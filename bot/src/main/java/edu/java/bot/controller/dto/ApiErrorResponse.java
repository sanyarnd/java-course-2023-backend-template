package edu.java.bot.controller.dto;

import java.util.Arrays;
import java.util.List;
import lombok.Data;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Data
public class ApiErrorResponse {
    private static final String METHOD_ARGUMENT_NOT_VALID_EXCEPTION_DESCRIPTION = "Были введены неверные параметры";

    String description;
    String code;
    String exceptionName;
    String exceptionMessage;
    List<String> stackTrace;

    public ApiErrorResponse(MethodArgumentNotValidException ex) {
        this.description = METHOD_ARGUMENT_NOT_VALID_EXCEPTION_DESCRIPTION;
        this.code = "400";
        this.exceptionName = "MethodArgumentNotValidException";
        this.exceptionMessage = ex.getMessage();
        this.stackTrace = Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).toList();
    }
}
