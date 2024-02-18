package edu.java.bot.view.command;

import com.pengrad.telegrambot.model.Update;
import java.util.regex.Pattern;

public interface CommandHandler {
    Pattern getPattern();

    void handle(Update update);
}
