package edu.java.scrapper.data.db;

import edu.java.core.exception.UserAlreadyAuthorizedException;
import edu.java.core.exception.UserIsNotAuthorizedException;
import edu.java.scrapper.data.db.entity.TelegramChat;

public interface TelegramChatRepository extends CrudRepository<TelegramChat, Long> {
    void registerTelegramChat(TelegramChat telegramChat) throws UserAlreadyAuthorizedException;

    void unregisterTelegramChat(TelegramChat telegramChat) throws UserIsNotAuthorizedException;
}
