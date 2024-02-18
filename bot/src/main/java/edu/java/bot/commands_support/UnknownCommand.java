package edu.java.bot.commands_support;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Command;

public class UnknownCommand implements Command {

    @Override
    public String command() {
        return "Unknown command. Use /help to see the available commands";
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), command());
    }
}
