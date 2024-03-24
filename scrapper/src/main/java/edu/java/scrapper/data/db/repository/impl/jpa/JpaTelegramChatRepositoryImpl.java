package edu.java.scrapper.data.db.repository.impl.jpa;

import edu.java.core.exception.UserAlreadyAuthorizedException;
import edu.java.core.exception.UserIsNotAuthorizedException;
import edu.java.scrapper.data.db.entity.TelegramChat;
import edu.java.scrapper.data.db.repository.TelegramChatRepository;
import edu.java.scrapper.data.db.repository.impl.jpa.dao.TelegramChatDao;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

@AllArgsConstructor
public class JpaTelegramChatRepositoryImpl implements TelegramChatRepository {
    private final TelegramChatDao chatDao;

    @Override
    public Optional<TelegramChat> get(Long entityId) {
        return chatDao.findById(entityId);
    }

    @Override
    public List<TelegramChat> getAll() {
        return chatDao.findAll();
    }

    @Override
    public void create(TelegramChat entity) throws UserAlreadyAuthorizedException {
        try {
            chatDao.create(entity.getId(), entity.getRegisteredAt());
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new UserAlreadyAuthorizedException(entity.getId());
        }
    }

    @Override
    public void delete(TelegramChat entity) throws UserIsNotAuthorizedException {
        int rowsAffected = chatDao.delete(entity.getId(), entity.getRegisteredAt());
        if (rowsAffected == 0) {
            throw new UserIsNotAuthorizedException(entity.getId());
        }
    }

    @Override
    public void update(TelegramChat entity) {
        chatDao.update(entity.getId(), entity.getRegisteredAt());
    }

    @Override
    public void upsert(TelegramChat entity) {
        chatDao.save(entity);
    }
}
