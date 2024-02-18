package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.tracking.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand implements Command {

    private final TrackingService trackingService;

    @Autowired
    public TrackCommand(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "Отслеживать ссылку";
    }

    @Override
    public SendMessage handle(Update update) {
        if (update.message().text().split(" ").length < 2) {
            return new SendMessage(getChatId(update), "Необходимо указать url");
        }
        var url = update.message().text().split(" ")[1];

        var chatId = getChatId(update);

        if (trackingService.addTracking(chatId, url)) {
            return new SendMessage(chatId, "Добавлен новый url: %s".formatted(url));
        }

        return new SendMessage(chatId, "Такой url уже отслеживается");
    }
}
