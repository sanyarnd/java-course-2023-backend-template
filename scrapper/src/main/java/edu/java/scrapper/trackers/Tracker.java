package edu.java.scrapper.trackers;

public interface Tracker {
    void addTrack(long user_id, String resource);

    void removeTrack(long user_id, String resource);

    void start();
}
