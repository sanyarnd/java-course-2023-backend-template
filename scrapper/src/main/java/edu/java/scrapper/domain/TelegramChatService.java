package edu.java.scrapper.domain;

public interface TelegramChatService {
    void register(Long telegramChatId);

    void unregister(Long telegramChatId);
}
