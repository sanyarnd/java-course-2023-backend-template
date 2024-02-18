package edu.java.bot.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface ICommand {
    String command();

    String description();

    SendMessage handle(Update update);

    default boolean supports(Update update) {
        return update.message().text().startsWith(command());
    }

    default SendMessage userResponseHandler(Update update) {
        return null;
    }

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }
}
