package edu.java.bot.bot.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.Objects;

public interface Command {
    String command();

    String description();

    SendMessage handle(Update update);

    default boolean supports(Update update) {
        if (!Objects.equals(update.message().text(), "")) {
            return true;
        }

        return false;
    }

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }
}
