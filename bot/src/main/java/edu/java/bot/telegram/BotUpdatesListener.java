package edu.java.bot.telegram;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import java.util.List;
import java.util.Objects;
import edu.java.bot.model.UserMessage;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Component
public class BotUpdatesListener implements UpdatesListener {

    private final ObjectProvider<TelegramRequestService> telegramRequestService;

    public BotUpdatesListener(ObjectProvider<TelegramRequestService> telegramRequestService) {
        this.telegramRequestService = telegramRequestService;
    }

    @Override
    public int process(List<Update> updates) {
        updates.stream().filter(it -> it.message() != null).forEach(this::process);
        return CONFIRMED_UPDATES_ALL;
    }

    private void process(Update update) {
        Objects.requireNonNull(telegramRequestService.getIfAvailable())
            .processMessage(new UserMessage(update.message().text(), update.message().chat().id()));
    }
}
