package edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface Command {
    SendMessage processCommand(Update update);

    String type();

    String description();

    default BotCommand toApiCommand() {
        return new BotCommand(type(), description());
    }
}
