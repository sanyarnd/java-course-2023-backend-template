package edu.java.bot.data;

import edu.java.bot.util.LinkAlreadyTrackedException;
import edu.java.bot.util.LinkIsNotTrackedException;
import edu.java.bot.util.UserAlreadyRegisteredException;
import edu.java.bot.util.UserIsNotRegisteredException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MockedTrackerRepository implements TrackerRepository {
    private final static HashMap<String, HashSet<String>> MOCKED_DB = new HashMap<>();

    @Override
    public void registerUser(String userId) {
        if (isUserRegistered(userId)) {
            throw new UserAlreadyRegisteredException();
        }
        MOCKED_DB.put(userId, new HashSet<>());
    }

    @Override
    public boolean isUserRegistered(String userId) {
        return MOCKED_DB.containsKey(userId);
    }

    @Override
    public void subscribeToLinkUpdates(String userId, String link) {
        if (!isUserRegistered(userId)) {
            throw new UserIsNotRegisteredException();
        }
        HashSet<String> trackedLinks = MOCKED_DB.get(userId);
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
        HashSet<String> trackedLinks = MOCKED_DB.get(userId);
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
        return MOCKED_DB.get(userId).stream().toList();
    }
}
