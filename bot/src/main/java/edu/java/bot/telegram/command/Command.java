package edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;

public interface Command {
    void processCommand(Update update);

    String type();

    String description();

    default BotCommand toApiCommand() {
        return new BotCommand(type(), description());
    }
}
