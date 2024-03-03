package edu.java.controller.exception;

public class LinkReAddingException extends RuntimeException implements IAPIError {

    public LinkReAddingException(String message) {
        super(message);
    }

    @Override
    public String getDescription() {
        return "Пользователь с таким id уже подписан на данную ссылку";
    }

    @Override
    public String getCode() {
        return "400";
    }

    @Override
    public String getName() {
        return "LinkReAddingException";
    }
}
