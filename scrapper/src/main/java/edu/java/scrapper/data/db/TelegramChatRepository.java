package edu.java.scrapper.data.db;

import edu.java.core.exception.UserAlreadyRegistered;
import edu.java.core.exception.UserNotRegistered;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.TelegramChat;
import java.util.List;

public interface TelegramChatRepository {
    void registerTelegramChat(TelegramChat telegramChat) throws UserAlreadyRegistered;

    void unregisterTelegramChat(TelegramChat telegramChat) throws UserNotRegistered;

    List<TelegramChat> findAllChatsSubscribedTo(Link link);
}
