package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Update;
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
    public String processCommand(Update update) {
        if (!usersWaiting.getWaiting(update.message().chat().id()).equals(getName())) {
            return requestURL(update);
        } else {
            return removeURL(update);
        }
    }

    private String requestURL(Update update) {
        if (!usersTracks.containsUser(update.message().chat().id())) {
            return BAD_USER_ID;
        }
        usersWaiting.setWaiting(update.message().chat().id(), getName());
        return REQUEST;
    }

    private String removeURL(Update update) {
        usersWaiting.setWaiting(update.message().chat().id(), usersWaiting.getDefaultWaiting());
        if (!usersTracks.removeTrackedURLs(update.message().chat().id(), update.message().text())) {
            return BAD_URL;
        } else {
            return URL_REMOVED;
        }

    }
}
