package edu.java.controller.exception;

public class ChatReAddingException extends RuntimeException implements IAPIError {
    public ChatReAddingException(String message) {
        super(message);
    }

    @Override
    public String getDescription() {
        return "Пользователь с таким id уже присутсвует в системе";
    }

    @Override
    public String getCode() {
        return "400";
    }

    @Override
    public String getName() {
        return "ChatReAddingException";
    }
}
