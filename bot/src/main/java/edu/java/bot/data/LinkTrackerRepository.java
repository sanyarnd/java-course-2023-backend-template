package edu.java.bot.data;

import java.util.List;

public interface LinkTrackerRepository {
    List<String> getUserTrackedLinks(Long userId)
        throws UserNotAuthenticated;

    void setLinkTracked(Long userId, String link)
        throws UserNotAuthenticated, LinkAlreadyTracked, LinkIsUnreachable;

    void setLinkUntracked(Long userId, String link)
        throws UserNotAuthenticated, LinkNotTracked;
}
