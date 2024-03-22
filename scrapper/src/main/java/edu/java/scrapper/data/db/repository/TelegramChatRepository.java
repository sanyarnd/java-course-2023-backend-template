package edu.java.scrapper.data.db.repository;

import edu.java.core.exception.UserAlreadyAuthorizedException;
import edu.java.scrapper.data.db.ReadRepository;
import edu.java.scrapper.data.db.WriteRepository;
import edu.java.scrapper.data.db.entity.TelegramChat;

public interface TelegramChatRepository extends ReadRepository<TelegramChat, Long>, WriteRepository<TelegramChat> {
    @Override
    void create(TelegramChat entity) throws UserAlreadyAuthorizedException;

    @Override
    void delete(TelegramChat entity) throws UserAlreadyAuthorizedException;
}
