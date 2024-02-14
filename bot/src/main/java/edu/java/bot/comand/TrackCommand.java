package edu.java.bot.comand;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.impl.UpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrackCommand implements Command {

    private final UpdateService updateService;

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "Start tracking URL";
    }

    @Override
    public SendMessage handle(Update update) {
        Long id = update.message().chat().id();
        String messageText = update.message().text();
        String[] parts = messageText.split("\\s+", 2);

        if (parts.length < 2) {
            return new SendMessage(id, "Usage: /track <URL>");
        }

        String url = parts[1];
        if (updateService.checkResourceURL(id, url)) {
            return new SendMessage(id, "Started tracking: " + url);
        } else {
            return new SendMessage(id, "This URL is not supported for tracking.");
        }
    }
}
