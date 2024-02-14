package edu.java.bot.comand;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.UrlRepository;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand implements Command {

    private final UrlRepository urlRepository = UrlRepository.getInstance();

    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "Stop tracking url";
    }

    @Override
    public SendMessage handle(Update update) {
        Long id = update.message().chat().id();
        String messageText = update.message().text();
        String[] parts = messageText.split("\\s+", 2);

        if (parts.length < 2) {
            return new SendMessage(id, "Usage: /untrack <URL>");
        }

        String url = parts[1];
        if (urlRepository.removeUrl(id, url)) {
            return new SendMessage(id, "Untracking: " + url);
        } else {
            return new SendMessage(id, "This url was not found");
        }

    }
}
