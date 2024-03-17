package edu.java.scrapper.domain.impl;

import edu.java.scrapper.data.db.TelegramChatRepository;
import edu.java.scrapper.data.db.entity.TelegramChat;
import edu.java.scrapper.domain.TelegramChatService;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;

@Service
public class TelegramChatServiceImpl implements TelegramChatService {
    private final TelegramChatRepository telegramChatRepository;

    public TelegramChatServiceImpl(TelegramChatRepository telegramChatRepository) {
        this.telegramChatRepository = telegramChatRepository;
    }

    @Override
    public void register(Long telegramChatId) {
        telegramChatRepository.registerTelegramChat(new TelegramChat(telegramChatId, OffsetDateTime.now()));
    }

    @Override
    public void unregister(Long telegramChatId) {
        telegramChatRepository.unregisterTelegramChat(new TelegramChat().setId(telegramChatId));
    }
}
