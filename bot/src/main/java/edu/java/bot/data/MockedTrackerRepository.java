package edu.java.bot.data;

import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class MockedTrackerRepository implements TrackerRepository {
    @Override
    public void registerUser(String userId) {

    }

    @Override
    public boolean isUserRegistered(String userId) {
        return false;
    }

    @Override
    public void subscribeToLinkUpdates(String userId, String link) {

    }

    @Override
    public void unsubscribeToLinkUpdates(String userId, String link) {

    }

    @Override
    public List<String> getUserSubscriptions(String userId) {
        return List.of();
    }
}
