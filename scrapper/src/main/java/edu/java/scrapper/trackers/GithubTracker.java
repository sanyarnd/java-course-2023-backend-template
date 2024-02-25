package edu.java.scrapper.trackers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.web.client.RestClient;

public class GithubTracker implements Tracker {
    private final RestClient restClient;
    private final Lock R_LOCK = new ReentrantLock();
    private final HashMap<String, List<Long>> resources = new HashMap<>();

    public GithubTracker() {
        this.restClient = RestClient.create("https://github.com/");
    }

    @Override
    public void addTrack(long user_id, String resource) {
        R_LOCK.lock();
        try {
            if (!resources.containsKey(resource)) {
                resources.put(resource, new ArrayList<Long>());
            }
            resources.get(resource).add(user_id);
        } finally {
            R_LOCK.unlock();
        }
    }

    @Override
    public void removeTrack(long user_id, String resource) {
        R_LOCK.lock();
        try {
            if (!resources.containsKey(resource)) {
                return;
            }
            resources.get(resource).remove(user_id);
            if (resources.get(resource).isEmpty()) {
                resources.remove(resource);
            }
        } finally {
            R_LOCK.unlock();
        }
    }

    @Override
    public void start() {

    }
}
