package edu.java.bot.services.tracking;

import java.util.List;

public interface TrackingService {
    boolean addTracking(Long user, String url);

    boolean removeTracking(Long user, String url);

    List<String> getTrackings(Long user);
}
