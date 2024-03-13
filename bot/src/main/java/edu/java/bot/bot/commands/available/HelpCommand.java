package edu.java.bot.bot.commands.available;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.bot.commands.Command;

public class HelpCommand implements Command {
    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "this is the help command";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), "help");
    }
}
