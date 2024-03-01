package edu.java.scrapper.clients.impl;

import edu.java.scrapper.clients.Client;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.web.client.RestClient;

public class GitHubClient implements Client {
    private final Lock rLock = new ReentrantLock();
    private final HashMap<String, List<Long>> resources = new HashMap<>();
    private final RestClient restClient;

    public GitHubClient() {
        this.restClient = RestClient.create("https://github.com/");
    }

    @Override
    public void addTrack(long userId, String resource) {
        rLock.lock();
        try {
            if (!resources.containsKey(resource)) {
                resources.put(resource, new ArrayList<>());
            }
            resources.get(resource).add(userId);
        } finally {
            rLock.unlock();
        }
    }

    @Override
    public void removeTrack(long userId, String resource) {
        rLock.lock();
        try {
            if (!resources.containsKey(resource)) {
                return;
            }
            resources.get(resource).remove(userId);
            if (resources.get(resource).isEmpty()) {
                resources.remove(resource);
            }
        } finally {
            rLock.unlock();
        }
    }

    @Override
    public void start() {

    }
}
