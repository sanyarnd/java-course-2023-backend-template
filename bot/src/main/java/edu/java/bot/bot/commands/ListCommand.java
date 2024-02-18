package edu.java.bot.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class ListCommand implements Command {
    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "Command to see all tracking urls";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), "list");
    }
}
