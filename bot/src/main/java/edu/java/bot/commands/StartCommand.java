package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Command;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {
    @Override
    public String command() {
        //user ID added to data base
        return "/start";
    }

    @Override
    public String description() {
        return "register new user for tracking";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), "You are registered for resource tracking");
    }
}
