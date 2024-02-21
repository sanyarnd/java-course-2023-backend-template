package edu.java.bot.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.user.UserService;

public interface Command {
    String command();

    String description();

    SendMessage handle(Update update);

    boolean supports(Update update, UserService userService);

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }
}
