package edu.java.bot.bot.commands.available;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.bot.commands.Command;

public class UntrackCommand implements Command {
    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "untrack some url example: '/untrack google.com'";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), "untracking");

    }
}
