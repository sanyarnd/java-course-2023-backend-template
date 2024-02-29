package edu.java.scrapper.clients;

public interface Client {
    void addTrack(long user_id, String resource);

    void removeTrack(long user_id, String resource);

    void start();
}
