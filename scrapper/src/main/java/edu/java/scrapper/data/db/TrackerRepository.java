package edu.java.scrapper.data.db;

import edu.java.core.exception.LinkAlreadyTrackedException;
import edu.java.core.exception.LinkIsNotRegisteredException;
import edu.java.core.exception.LinkIsNotTrackedException;
import edu.java.core.exception.UserIsNotAuthorizedException;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.TelegramChat;
import java.util.List;

public interface TrackerRepository {
    void track(Link link, TelegramChat telegramChat)
            throws LinkAlreadyTrackedException, LinkIsNotRegisteredException, UserIsNotAuthorizedException;

    void untrack(Link link, TelegramChat telegramChat)
            throws LinkIsNotTrackedException, LinkIsNotRegisteredException, UserIsNotAuthorizedException;

    List<TelegramChat> findAllChatsSubscribedTo(Link link);

    List<Link> findAllLinksSubscribedWith(TelegramChat chat);
}
