package edu.java.bot.view.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.Optional;
import java.util.regex.Pattern;

public interface CommandHandler {
    String command();
    String description();

    Optional<SendMessage> handle(Update update);

    default Pattern getPattern() {
        return Pattern.compile(command());
    }

    default BotCommand convertToApi() {
        return new BotCommand(command(), description());
    }
}
