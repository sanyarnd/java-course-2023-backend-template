package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.data.UsersTracks;
import edu.java.bot.services.ICommand;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements ICommand {
    private UsersTracks usersTracks;
    private static final String NO_TRACKED = "В данный момент вы не подписаны не на одну ссылку";
    private static final String ANY_TRACKED = "В данный момент вы подписаны на следующие ссылки:\n";

    @Autowired ListCommand(UsersTracks usersTracks) {
        this.usersTracks = usersTracks;
    }

    @Override
    public String getName() {
        return "/list";
    }

    @Override
    public String getDescription() {
        return "Показать список отслеживаемых ссылок";
    }

    @Override
    public String processCommand(Update update) {
        HashSet<String> urls = usersTracks.getTrackedURLs(update.message().chat().id());
        if (urls == null || urls.isEmpty()) {
            return NO_TRACKED;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(ANY_TRACKED);
            for (String url : urls) {
                stringBuilder.append(url);
                stringBuilder.append("\n");
            }
            stringBuilder.trimToSize();
            return stringBuilder.toString();
        }
    }
}
