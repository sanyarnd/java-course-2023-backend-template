package edu.java.scrapper.data.db.repository.impl.jpa;

import edu.java.core.exception.UserAlreadyAuthorizedException;
import edu.java.core.exception.UserIsNotAuthorizedException;
import edu.java.scrapper.data.db.entity.TelegramChat;
import edu.java.scrapper.data.db.repository.TelegramChatRepository;
import java.util.List;
import java.util.Optional;

public class JpaTelegramChatRepositoryImpl implements TelegramChatRepository {
    @Override
    public Optional<TelegramChat> get(Long entityId) {
        return Optional.empty();
    }

    @Override
    public List<TelegramChat> getAll() {
        return null;
    }

    @Override
    public void update(TelegramChat entity) {

    }

    @Override
    public void upsert(TelegramChat entity) {

    }

    @Override
    public void create(TelegramChat entity) throws UserAlreadyAuthorizedException {

    }

    @Override
    public void delete(TelegramChat entity) throws UserIsNotAuthorizedException {

    }
}
