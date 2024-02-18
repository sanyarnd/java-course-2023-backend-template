package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Command;

public class TrackCommand implements Command {
    @Override
    public String command() {
        return "Input link for tracking:";
    }

    public String addLink(String link) {
        //add link to db;
        return link;
    }

    @Override
    public String description() {
        return "/track — start tracking the link";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), command());
    }
}
