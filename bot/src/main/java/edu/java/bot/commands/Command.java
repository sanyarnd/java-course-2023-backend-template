package edu.java.bot.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.user.UserService;

public interface Command {
    String command();

    String description();

    SendMessage handle(Update update);

    default boolean supports(Update update, UserService userService) {
        if (update.message() != null && update.message().text() != null) {
            String messageText = update.message().text();
            Long userId = update.message().from().id();
            boolean isCommandMatch = messageText.startsWith(command());
            boolean isUserRegistered = userService.isRegistered(userId) || command().equals("/start");

            return isCommandMatch && isUserRegistered;
        }
        return false;
    }

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }
}
