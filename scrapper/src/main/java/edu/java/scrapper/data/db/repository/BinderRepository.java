package edu.java.scrapper.data.db.repository;

import edu.java.core.exception.LinkAlreadyTrackedException;
import edu.java.core.exception.LinkIsNotRegisteredException;
import edu.java.core.exception.LinkIsNotTrackedException;
import edu.java.core.exception.UserIsNotAuthorizedException;
import edu.java.scrapper.data.db.WriteRepository;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.TelegramChat;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public interface BinderRepository extends WriteRepository<Pair<TelegramChat, Link>> {
    @Override
    void create(Pair<TelegramChat, Link> entity) throws LinkAlreadyTrackedException;

    @Override
    void delete(Pair<TelegramChat, Link> entity) throws LinkIsNotTrackedException;

    @Override
    void update(Pair<TelegramChat, Link> entity) throws IllegalStateException;

    @Override
    void upsert(Pair<TelegramChat, Link> entity) throws IllegalStateException;

    List<TelegramChat> findAllChatsSubscribedTo(Link link);

    List<Link> findAllLinksSubscribedWith(TelegramChat telegramChat);
}
