package edu.java.scrapper.data.db.repository.impl.jpa;

import edu.java.core.exception.LinkAlreadyTrackedException;
import edu.java.core.exception.LinkIsNotTrackedException;
import edu.java.scrapper.data.db.entity.Binding;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.TelegramChat;
import edu.java.scrapper.data.db.repository.BindingRepository;
import java.util.List;

public class JpaBindingRepositoryImpl implements BindingRepository {
    @Override
    public void create(Binding entity) throws LinkAlreadyTrackedException {

    }

    @Override
    public void delete(Binding entity) throws LinkIsNotTrackedException {

    }

    @Override
    public void update(Binding entity) throws IllegalStateException {

    }

    @Override
    public void upsert(Binding entity) throws IllegalStateException {

    }

    @Override
    public List<TelegramChat> findAllChatsSubscribedTo(Link link) {
        return null;
    }

    @Override
    public List<Link> findAllLinksSubscribedWith(TelegramChat telegramChat) {
        return null;
    }
}
