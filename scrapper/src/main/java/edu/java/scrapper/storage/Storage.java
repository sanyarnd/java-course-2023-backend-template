package edu.java.scrapper.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Storage {
    private final HashMap<Long, ArrayList<String>> resources = new HashMap<>();

    public void addResource(long userId, String resource) {
        if (!resources.containsKey(userId)) {
            resources.put(userId, new ArrayList<String>());
        }
        resources.get(userId).add(resource);
    }

    public void removeResource(long userId, String resource) {
        if (!resources.containsKey(userId)) {
            resources.put(userId, new ArrayList<String>());
        }
        resources.get(userId).remove(resource);
    }

    public List<String> getResources(long userId) {
        if (!resources.containsKey(userId)) {
            resources.put(userId, new ArrayList<String>());
        }
        return resources.get(userId);
    }
}
