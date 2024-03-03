package edu.java.controller.exception;

public class LinkNotFoundException extends RuntimeException implements IAPIError {
    public LinkNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getDescription() {
        return "Не найдена ссылка с таким url";
    }

    @Override
    public String getCode() {
        return "404";
    }

    @Override
    public String getName() {
        return "LinkNotFoundException";
    }
}
