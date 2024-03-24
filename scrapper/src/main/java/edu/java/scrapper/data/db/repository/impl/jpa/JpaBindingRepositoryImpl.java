package edu.java.scrapper.data.db.repository.impl.jpa;

import edu.java.core.exception.LinkAlreadyTrackedException;
import edu.java.core.exception.LinkIsNotTrackedException;
import edu.java.scrapper.data.db.entity.Binding;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.TelegramChat;
import edu.java.scrapper.data.db.repository.BindingRepository;
import edu.java.scrapper.data.db.repository.impl.jpa.dao.BindingDao;
import edu.java.scrapper.data.db.repository.impl.jpa.dao.LinkDao;
import edu.java.scrapper.data.db.repository.impl.jpa.dao.TelegramChatDao;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

@AllArgsConstructor
public class JpaBindingRepositoryImpl implements BindingRepository {
    private final static String NOT_SUPPORTED_DESCRIPTION = "This method is not supported!";

    private final BindingDao bindingDao;
    private final LinkDao linkDao;
    private final TelegramChatDao telegramChatDao;

    @Override
    public void create(Binding entity) throws LinkAlreadyTrackedException {
        try {
            bindingDao.create(entity.getChatId(), entity.getLinkId());
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new LinkAlreadyTrackedException(entity.getLinkId(), entity.getChatId());
        }
    }

    @Override
    public void delete(Binding entity) throws LinkIsNotTrackedException {
        int rowsAffected = bindingDao.delete(entity.getChatId(), entity.getLinkId());
        if (rowsAffected == 0) {
            throw new LinkIsNotTrackedException(entity.getLinkId(), entity.getChatId());
        }
    }

    @Override
    public void update(Binding entity) throws IllegalStateException {
        throw new IllegalStateException(NOT_SUPPORTED_DESCRIPTION);
    }

    @Override
    public void upsert(Binding entity) throws IllegalStateException {
        throw new IllegalStateException(NOT_SUPPORTED_DESCRIPTION);
    }

    @Override
    public List<TelegramChat> findAllChatsSubscribedTo(Link link) {
        return bindingDao.findAllByLinkId(link.getId())
                .stream()
                .map(Binding::getChatId)
                .map(telegramChatDao::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Override
    public List<Link> findAllLinksSubscribedWith(TelegramChat telegramChat) {
        return bindingDao.findAllByChatId(telegramChat.getId())
                .stream()
                .map(Binding::getLinkId)
                .map(linkDao::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}
