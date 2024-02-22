package edu.java.bot.service.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public abstract class Command {
    public abstract String command();

    public abstract String description();

    public boolean supports(Update update) {
        return update.message() != null && update.message().text() != null
            && update.message().text().startsWith("/" + command());
    }

    public abstract SendMessage process(Update update);

    public BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }

    @Override
    public String toString() {
        return "/" + command() + " - " + description();
    }
}
