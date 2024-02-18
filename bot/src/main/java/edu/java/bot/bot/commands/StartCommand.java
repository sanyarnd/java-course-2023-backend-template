package edu.java.bot.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class StartCommand implements Command {
    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "Start command to start or restart the bot";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), "you've started the bot");

    }
}
