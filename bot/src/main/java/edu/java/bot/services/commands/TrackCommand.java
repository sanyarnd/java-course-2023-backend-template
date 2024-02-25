package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.bot.Bot;
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
    public boolean processCommand(Bot bot, Update update) {
        if (!usersWaiting.getWaiting(update.message().chat().id()).equals(getName())) {
            return requestURL(bot, update);
        } else {
            return addURL(bot, update);
        }
    }

    private boolean requestURL(Bot bot, Update update) {
        bot.writeToUser(update, REQUEST);
        usersWaiting.setWaiting(update.message().chat().id(), getName());
        return true;
    }

    private boolean addURL(Bot bot, Update update) {
        usersTracks.addUser(update.message().chat().id());
        if (!usersTracks.addTrackedURLs(update.message().chat().id(), update.message().text())) {
            bot.writeToUser(update, BAD_URL);
        } else {
            bot.writeToUser(update, URL_ADDED);
        }
        usersWaiting.setWaiting(update.message().chat().id(), usersWaiting.getDefaultWaiting());
        return true;
    }
}
