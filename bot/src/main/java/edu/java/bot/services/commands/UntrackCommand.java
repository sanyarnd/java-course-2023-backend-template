package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.bot.Bot;
import edu.java.bot.data.UsersTracks;
import edu.java.bot.data.UsersWaiting;
import edu.java.bot.services.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand implements ICommand {
    final private UsersTracks usersTracks;
    final private UsersWaiting usersWaiting;
    private static final String REQUEST = "Пожалуйста, введите URL, который вы хотите перестать трэкать:";
    private static final String URL_REMOVED = "URL был удалён";
    private static final String BAD_URL = "Введённая строка не является url!";
    private static final String BAD_USER_ID = "Вы не прошли регистрацию или не добаваили ни одного url";

    @Autowired UntrackCommand(UsersTracks usersTracks, UsersWaiting usersWaiting) {
        this.usersTracks = usersTracks;
        this.usersWaiting = usersWaiting;
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
        if (!usersWaiting.getWaiting(update.message().chat().id()).equals(getName())) {
            return requestURL(bot, update);
        } else {
            return removeURL(bot, update);
        }
    }

    private boolean requestURL(Bot bot, Update update) {
        if (!usersTracks.containsUser(update.message().chat().id())) {
            bot.writeToUser(update, BAD_USER_ID);
            return true;
        }
        bot.writeToUser(update, REQUEST);
        usersWaiting.setWaiting(update.message().chat().id(), getName());
        return true;
    }

    private boolean removeURL(Bot bot, Update update) {
        if (!usersTracks.removeTrackedURLs(update.message().chat().id(), update.message().text())) {
            bot.writeToUser(update, BAD_URL);
        } else {
            bot.writeToUser(update, URL_REMOVED);
        }
        usersWaiting.setWaiting(update.message().chat().id(), usersWaiting.getDefaultWaiting());
        return true;
    }
}
