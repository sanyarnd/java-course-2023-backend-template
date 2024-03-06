package edu.java.bot.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface Command {
    String command();

    String description();

    SendMessage handle(Update update);

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }

    default boolean supports(Update update) {
        return update.message().text().trim().equals(command());
    }
}
