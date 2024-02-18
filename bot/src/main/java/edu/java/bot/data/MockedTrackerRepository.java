package edu.java.bot.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import edu.java.bot.util.LinkAlreadyTrackedException;
import edu.java.bot.util.LinkIsNotTrackedException;
import edu.java.bot.util.UserAlreadyRegisteredException;
import edu.java.bot.util.UserIsNotRegisteredException;
import org.springframework.stereotype.Repository;

@Repository
public class MockedTrackerRepository implements TrackerRepository {
    private final static HashMap<String, HashSet<String>> mockedDB = new HashMap<>();

    @Override
    public void registerUser(String userId) {
        if (isUserRegistered(userId)) {
            throw new UserAlreadyRegisteredException();
        }
        mockedDB.put(userId, new HashSet<>());
    }

    @Override
    public boolean isUserRegistered(String userId) {
        return mockedDB.containsKey(userId);
    }

    @Override
    public void subscribeToLinkUpdates(String userId, String link) {
        if (!isUserRegistered(userId)) {
            throw new UserIsNotRegisteredException();
        }
        HashSet<String> trackedLinks = mockedDB.get(userId);
        if (trackedLinks.contains(link)) {
            throw new LinkAlreadyTrackedException();
        }
        trackedLinks.add(link);
    }

    @Override
    public void unsubscribeToLinkUpdates(String userId, String link) {
        if (!isUserRegistered(userId)) {
            throw new UserIsNotRegisteredException();
        }
        HashSet<String> trackedLinks = mockedDB.get(userId);
        if (!trackedLinks.contains(link)) {
            throw new LinkIsNotTrackedException();
        }
        trackedLinks.remove(link);
    }

    @Override
    public List<String> getUserSubscriptions(String userId) {
        if (!isUserRegistered(userId)) {
            throw new UserIsNotRegisteredException();
        }
        return mockedDB.get(userId).stream().toList();
    }
}
