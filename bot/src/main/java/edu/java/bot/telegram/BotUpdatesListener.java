package edu.java.bot.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.model.UserMessage;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class BotUpdatesListener implements UpdatesListener {

    private final TelegramRequestService telegramRequestService;

    public BotUpdatesListener(TelegramRequestService telegramRequestService, TelegramBot telegramBot) {
        this.telegramRequestService = telegramRequestService;
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.stream()
            .map(Update::message)
            .filter(Objects::nonNull)
            .forEach(this::process);
        return CONFIRMED_UPDATES_ALL;
    }

    private void process(Message message) {
        if (message.text() == null) {
            return;
        }
        telegramRequestService.processMessage(new UserMessage(message.text(), message.chat().id()));
    }
}
