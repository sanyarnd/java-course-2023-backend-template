package edu.java.bot.data;

import edu.java.bot.model.LinkAlreadyNotTracked;
import edu.java.bot.model.LinkAlreadyTracked;
import edu.java.bot.model.LinkIsUnreachable;
import edu.java.bot.model.UserIsNotAuthenticated;
import java.util.List;

public interface LinkTrackerRepository {
    List<String> getUserTrackedLinks(Long userId)
        throws UserIsNotAuthenticated;

    void setLinkTracked(Long userId, String link)
        throws UserIsNotAuthenticated, LinkAlreadyTracked, LinkIsUnreachable;

    void setLinkUntracked(Long userId, String link)
        throws UserIsNotAuthenticated, LinkAlreadyNotTracked;
}
