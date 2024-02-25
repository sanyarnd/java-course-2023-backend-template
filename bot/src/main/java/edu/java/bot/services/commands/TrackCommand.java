package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.data.UsersTracks;
import edu.java.bot.data.UsersWaiting;
import edu.java.bot.services.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand implements ICommand {
    private static final String REQUEST = "Пожалуйста, введите URL, который надо трэкать:";
    private static final String URL_ADDED = "URL был добавлен";
    private static final String BAD_URL = "Введённая строка не является url!";
    private static final String BAD_USER_ID = "Извините, но сначала надо зарегестрироваться: /start";
    private UsersTracks usersTracks;
    private UsersWaiting usersWaiting;

    @Autowired TrackCommand(UsersTracks usersTracks, UsersWaiting usersWaiting) {
        this.usersTracks = usersTracks;
        this.usersWaiting = usersWaiting;
    }

    @Override
    public String getName() {
        return "/track";
    }

    @Override
    public String getDescription() {
        return "Начать отслеживание ссылки";
    }

    @Override
    public String processCommand(Update update) {
        if (!usersWaiting.getWaiting(update.message().chat().id()).equals(getName())) {
            return requestURL(update);
        } else {
            return addURL(update);
        }
    }

    private String requestURL(Update update) {
        if (!usersTracks.containsUser(update.message().chat().id())) {
            return BAD_USER_ID;
        }
        usersWaiting.setWaiting(update.message().chat().id(), getName());
        return REQUEST;
    }

    private String addURL(Update update) {
        usersWaiting.setWaiting(update.message().chat().id(), usersWaiting.getDefaultWaiting());
        if (!usersTracks.addTrackedURLs(update.message().chat().id(), update.message().text())) {
            return BAD_URL;
        } else {
            return URL_ADDED;
        }
    }
}
