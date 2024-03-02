package edu.java.bot.data;

import edu.java.bot.model.LinkAlreadyTrackedException;
import edu.java.bot.model.LinkIsNotTrackedException;
import edu.java.bot.model.UserAlreadyRegisteredException;
import edu.java.bot.model.UserIsNotRegisteredException;
import java.util.List;

public interface TrackerRepository {
    void registerUser(String userId) throws UserAlreadyRegisteredException;

    boolean isUserRegistered(String userId);

    void subscribeToLinkUpdates(String userId, String link)
        throws UserIsNotRegisteredException, LinkAlreadyTrackedException;

    void unsubscribeToLinkUpdates(String userId, String link)
        throws UserIsNotRegisteredException, LinkIsNotTrackedException;

    List<String> getUserSubscriptions(String userId) throws UserIsNotRegisteredException;
}
