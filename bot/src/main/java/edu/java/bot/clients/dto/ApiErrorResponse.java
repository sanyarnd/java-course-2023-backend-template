package edu.java.bot.clients.dto;

import java.util.List;
import lombok.Data;

@Data
public class ApiErrorResponse {
    String description;
    String code;
    String exceptionName;
    String exceptionMessage;
    List<String> stackTrace;
}
