package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Command;

public class StartCommand implements Command {
    @Override
    public String command() {
        //user ID added to data base
        return "You are registered for resource tracking";
    }

    @Override
    public String description() {
        return "/start — register new user for tracking";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), command());
    }
}
