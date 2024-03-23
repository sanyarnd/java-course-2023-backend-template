package edu.java.scrapper.data.db.repository;

import edu.java.core.exception.LinkAlreadyTrackedException;
import edu.java.core.exception.LinkIsNotTrackedException;
import edu.java.scrapper.data.db.WriteRepository;
import edu.java.scrapper.data.db.entity.Binding;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.TelegramChat;
import java.util.List;

public interface BindingRepository extends WriteRepository<Binding> {
    @Override
    void create(Binding entity) throws LinkAlreadyTrackedException;

    @Override
    void delete(Binding entity) throws LinkIsNotTrackedException;

    @Override
    void update(Binding entity) throws IllegalStateException;

    @Override
    void upsert(Binding entity) throws IllegalStateException;

    List<TelegramChat> findAllChatsSubscribedTo(Link link);

    List<Link> findAllLinksSubscribedWith(TelegramChat telegramChat);
}
