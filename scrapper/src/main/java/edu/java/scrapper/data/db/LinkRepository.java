package edu.java.scrapper.data.db;

import edu.java.core.exception.link.LinkAlreadyRegistered;
import edu.java.core.util.BaseRepository;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.TelegramChat;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface LinkRepository extends BaseRepository<Link, Long> {
    Link registerLink(Link link) throws LinkAlreadyRegistered;

    void update(Link link);

    void subscribe(Link link, TelegramChat telegramChat);

    void unsubscribe(Link link, TelegramChat telegramChat);

    List<Link> findAllLinksSubscribedWith(TelegramChat chat);

    List<Link> findAllLinksUpdatedBefore(OffsetDateTime offsetDateTime);

    Optional<Link> findByUrl(String url);
}
