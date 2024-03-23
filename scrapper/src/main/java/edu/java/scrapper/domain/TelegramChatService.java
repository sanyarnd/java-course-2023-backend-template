package edu.java.scrapper.domain;

import edu.java.core.exception.UserAlreadyAuthorizedException;
import edu.java.core.exception.UserIsNotAuthorizedException;

public interface TelegramChatService {
    void register(Long telegramChatId) throws UserAlreadyAuthorizedException;

    void unregister(Long telegramChatId) throws UserIsNotAuthorizedException;
}
