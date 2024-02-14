package edu.java.bot.comand;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.UrlRepository;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {

    private final UrlRepository urlRepository;

    public ListCommand() {
        this.urlRepository = UrlRepository.getInstance();
    }

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "Outputs a list of tracked links";
    }

    @Override
    public SendMessage handle(Update update) {
        Long id = update.message().chat().id();
        Set<String> urls = urlRepository.getUrls(id);

        if (urls.isEmpty()) {
            return new SendMessage(id, "You don't have any tracking links");
        }

        StringBuilder helpUrls = new StringBuilder();

        urls.forEach(u -> helpUrls.append(u).append("\n"));

        return new SendMessage(id, helpUrls.toString());
    }
}
