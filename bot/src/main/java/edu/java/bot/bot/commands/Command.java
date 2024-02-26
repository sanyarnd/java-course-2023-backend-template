package edu.java.bot.bot.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface Command {
    String command();

    String description();

    SendMessage handle(Update update);

    default boolean supports(Update update) {
        return update.message().text().split(" ")[0].equals(this.command());
    }

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }
}
