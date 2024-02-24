package edu.java.bot.model;

public record Link(String host, String path, String query) {

    @Override
    public String toString() {
        return host + (path != null ? path : "") + (query != null ? query : "");
    }
}
