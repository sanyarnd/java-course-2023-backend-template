package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.bot.Bot;
import edu.java.bot.data.UsersTracks;
import edu.java.bot.services.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand implements ICommand {
    final private UsersTracks usersTracks;
    private boolean isWaiting = false;
    private static final String REQUEST = "Пожалуйста, введите URL, который вы хотите перестать трэкать:";
    private static final String URL_REMOVED = "URL был удалён";
    private static final String BAD_URL = "Введённая строка не является url!";

    @Autowired UntrackCommand(UsersTracks usersTracks) {
        this.usersTracks = usersTracks;
    }

    @Override
    public boolean isWaiting() {
        return isWaiting;
    }

    @Override
    public String getName() {
        return "/untrack";
    }

    @Override
    public String getDescription() {
        return "Прекратить отслеживание ссылки";
    }

    @Override
    public boolean processCommand(Bot bot, Update update) {
        if (!isWaiting) {
            return requestURL(bot, update);
        } else {
            return removeURL(bot, update);
        }
    }

    private boolean requestURL(Bot bot, Update update) {
        usersTracks.addUser(update.message().chat().id());
        bot.writeToUser(update, REQUEST);
        isWaiting = true;
        return true;
    }

    private boolean removeURL(Bot bot, Update update) {
        isWaiting = false;
        if (!usersTracks.removeTrackedURLs(update.message().chat().id(), update.message().text())) {
            bot.writeToUser(update, BAD_URL);
            return true;
        }
        bot.writeToUser(update, URL_REMOVED);
        return true;
    }
}
