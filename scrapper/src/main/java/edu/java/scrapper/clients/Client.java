package edu.java.scrapper.clients;

public interface Client {
    void addTrack(long userId, String resource);

    void removeTrack(long userId, String resource);

    void start();
}
