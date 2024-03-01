package edu.java.scrapper.clients;

import edu.java.scrapper.clients.impl.GitHubClient;
import edu.java.scrapper.clients.impl.StackOverflowClient;

public final class TrackersManager {
    private TrackersManager()
    {}

    public static Client getTracker(String resource) {
        if (resource.contains("github")) {
            return new GitHubClient();
        } else if (resource.contains("stackoverflow")) {
            return new StackOverflowClient();
        } else {
            throw new RuntimeException("No such tracker");
        }
    }
}
