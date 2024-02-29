package edu.java.scrapper.controllers;

import edu.java.scrapper.storage.Storage;
import edu.java.scrapper.clients.TrackersManager;
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

    @RequestMapping(value = "/{user_id}", method = RequestMethod.GET)
    List<String> getResourceList(@PathVariable long user_id) {
        return storage.getResources(user_id);
    }

    @RequestMapping(method = RequestMethod.POST)
    void track(Long user_id, String resource) {
        var tracker = TrackersManager.getTracker(resource);
        tracker.addTrack(user_id, resource);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    void untrack(Long user_id, String resource) {
        var tracker = TrackersManager.getTracker(resource);
        tracker.removeTrack(user_id, resource);

    }
}
