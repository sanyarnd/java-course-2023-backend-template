package edu.java.bot.domain;

import edu.java.core.exception.ApiErrorException;
import lombok.Getter;

@Getter
public class ErrorTelegramResponse extends TelegramResponse {
    private final ApiErrorException apiException;

    public ErrorTelegramResponse(ApiErrorException apiException) {
        super(apiException.description);
        this.apiException = apiException;
    }
}
