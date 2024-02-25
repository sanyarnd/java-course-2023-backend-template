package edu.java.scrapper.trackers;

public class TrackersManager {
    public static Tracker getTracker(String resource) {
        if (resource.contains("github")) {
            return new GithubTracker();
        } else if (resource.contains("stackoverflow")) {
            return new StackoverflowTracker();
        } else {
            throw new RuntimeException("No such tracker");
        }
    }
}
