package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class HelpCommand implements ICommand {

    private final String helpText;

    public HelpCommand(String helpText) {
        this.helpText = helpText;
    }

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "вывести окно с командами";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), helpText);
    }
}
