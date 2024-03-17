package edu.java.scrapper.data.db;

import edu.java.core.exception.link.LinkAlreadyRegistered;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.TelegramChat;
import java.time.OffsetDateTime;
import java.util.List;

public interface LinkRepository {
    Link registerLink(Link link) throws LinkAlreadyRegistered;

    void update(Link link);

    void subscribe(Link link, TelegramChat telegramChat);

    void unsubscribe(Link link, TelegramChat telegramChat);

    List<Link> findAllLinksSubscribedWith(TelegramChat chat);

    List<Link> findAllLinksUpdatedBefore(OffsetDateTime offsetDateTime);
}
