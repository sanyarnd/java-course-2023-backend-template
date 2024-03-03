package edu.java.controller.exception;

public class CantHandleURLException extends RuntimeException implements IAPIError {
    public CantHandleURLException(String message) {
        super(message);
    }

    @Override
    public String getDescription() {
        return "Сервисс не работает с ссылками такого типа";
    }

    @Override
    public String getCode() {
        return "400";
    }

    @Override
    public String getName() {
        return "CantHandleURLException";
    }
}
