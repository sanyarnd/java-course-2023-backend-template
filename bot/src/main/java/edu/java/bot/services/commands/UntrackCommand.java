package edu.java.bot.services.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.bot.Bot;
import edu.java.bot.data.UsersTracks;
import edu.java.bot.services.ICommand;
import jakarta.validation.constraints.AssertFalse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand implements ICommand {
    final private UsersTracks usersTracks;

    @Autowired
    UntrackCommand(UsersTracks usersTracks) {
        this.usersTracks = usersTracks;
    }

    @Override
    public String getName() {
        return "/untrack";
    }

    @Override
    public boolean processCommand(Bot bot, Update update) {
        return false;
    }
}
