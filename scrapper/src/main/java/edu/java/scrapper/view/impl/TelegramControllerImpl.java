package edu.java.scrapper.view.impl;

import edu.java.scrapper.domain.TelegramChatService;
import edu.java.scrapper.view.TelegramController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TelegramControllerImpl implements TelegramController {
    private final TelegramChatService telegramChatService;

    public TelegramControllerImpl(TelegramChatService telegramChatService) {
        this.telegramChatService = telegramChatService;
    }

    @Override
    public ResponseEntity<Void> tgChatIdPost(Long id) {
        telegramChatService.register(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> tgChatIdDelete(Long id) {
        telegramChatService.unregister(id);
        return ResponseEntity.ok().build();
    }
}
