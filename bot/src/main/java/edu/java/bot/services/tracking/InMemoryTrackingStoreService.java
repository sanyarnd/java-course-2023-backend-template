package edu.java.bot.services.tracking;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class InMemoryTrackingStoreService implements TrackingService {
    private final Map<Long, HashSet<String>> trackingByUser;

    public InMemoryTrackingStoreService() {
        this.trackingByUser = new HashMap<>();
    }

    @Override
    public boolean addTracking(Long user, String url) {
        var set = trackingByUser.computeIfAbsent(user, k -> new HashSet<>());
        if (set.contains(url)) {
            return false;
        }

        set.add(url);
        return true;
    }

    @Override
    public boolean removeTracking(Long user, String url) {
        Set<String> urls = trackingByUser.get(user);
        if (urls != null) {
            urls.remove(url);
            return true;
        }

        return false;
    }

    @Override
    public List<String> getTrackings(Long user) {
        return trackingByUser.getOrDefault(user, new HashSet<>()).stream().toList();
    }
}
