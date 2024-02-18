package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Command;

public class ListCommand implements Command {
    @Override
    public String command() {
        //return list
        return "List of tracked links:";
    }

    @Override
    public String description() {
        return "/list — show a list of tracked links";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), command());
    }
}
