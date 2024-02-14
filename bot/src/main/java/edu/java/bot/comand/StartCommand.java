package edu.java.bot.comand;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "Start the bot and get welcome message";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), "Welcome to the Bot!");
    }
}
