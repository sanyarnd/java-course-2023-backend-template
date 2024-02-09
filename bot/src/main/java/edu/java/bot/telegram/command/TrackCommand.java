package edu.java.bot.telegram.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand implements Command {
    private final TelegramBot telegramBot;

    @Autowired
    public TrackCommand(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void processCommand(Update update) {

    }

    @Override
    public String type() {
        return CommandType.TRACK.getType();
    }

    @Override
    public String description() {
        return CommandType.TRACK.getDescription();
    }
}
