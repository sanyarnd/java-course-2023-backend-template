package edu.java.controller.exception;

import org.springframework.stereotype.Component;

public class ChatNotFoundException extends RuntimeException implements IAPIError {
    public ChatNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getDescription() {
        return "Не найден пользователь с таким id";
    }

    @Override
    public String getCode() {
        return "404";
    }

    @Override
    public String getName() {
        return "ChatNotFoundException";
    }
}
