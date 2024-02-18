package edu.java.bot;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.commands.HelpCommand;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.commands.UntrackCommand;
import edu.java.bot.commands_support.UnknownCommand;

public class CommandRecognizer {
    public Command recognize(Update update) {
        String messageText = update.message().text();
        Command command = switch (messageText) {
            case "/help" -> new HelpCommand();
            case "/start" -> new StartCommand();
            case "/list" -> new ListCommand();
            case "/track" -> new TrackCommand();
            case "/untrack" -> new UntrackCommand();
            default -> new UnknownCommand();
        };


        return command;
    }
}
