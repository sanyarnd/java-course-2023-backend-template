package edu.java.controller.exception;

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
