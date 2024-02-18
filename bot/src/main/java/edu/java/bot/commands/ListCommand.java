package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.tracking.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {
    private final TrackingService trackingService;

    @Autowired
    public ListCommand(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "Лист отслеживаемых ссылок";
    }

    @Override
    public SendMessage handle(Update update) {
        var chatId = getChatId(update);
        var trackings = trackingService.getTrackings(chatId);
        return new SendMessage(chatId, trackings.isEmpty()
            ? "Нет отслеживаемых ссылок"
            : "Список url:\n%s".formatted(String.join("\n", trackings))
        );
    }
}
