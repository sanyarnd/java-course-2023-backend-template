package edu.java.scrapper.controllers;

import edu.java.scrapper.clients.TrackersManager;
import edu.java.scrapper.storage.Storage;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resources")
public class Resource {
    private final Storage storage;

    @Autowired
    public Resource(Storage storage) {
        this.storage = storage;
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    List<String> getResourceList(@PathVariable long userId) {
        return storage.getResources(userId);
    }

    @RequestMapping(method = RequestMethod.POST)
    void track(Long userId, String resource) {
        var tracker = TrackersManager.getTracker(resource);
        tracker.addTrack(userId, resource);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    void untrack(Long userId, String resource) {
        var tracker = TrackersManager.getTracker(resource);
        tracker.removeTrack(userId, resource);

    }
}
