package edu.java.bot.services.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.bot.Bot;
import edu.java.bot.data.UsersTracks;
import edu.java.bot.services.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand implements ICommand {

    private UsersTracks usersTracks;

    @Autowired
    TrackCommand(UsersTracks usersTracks) {
        this.usersTracks = usersTracks;
    }

    @Override
    public String getName() {
        return "/track";
    }

    @Override
    public boolean processCommand(Bot bot, Update update) {
        return false;
    }
}
