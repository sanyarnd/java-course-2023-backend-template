package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.tracking.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand implements Command {

    private final TrackingService trackingService;

    @Autowired
    public UntrackCommand(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "Перестать отслеживать ссылку";
    }

    @Override
    public SendMessage handle(Update update) {
        if (update.message().text().split(" ").length < 2) {
            return new SendMessage(getChatId(update), "Необходимо указать url");
        }

        var url = update.message().text().split(" ")[1];
        var chatId = getChatId(update);
        var success = trackingService.removeTracking(chatId, url);
        if (success) {
            return new SendMessage(chatId, "Удален url: %s".formatted(url));
        }

        return new SendMessage(chatId, "Такого url нет в списке отслеживаемых");
    }
}
