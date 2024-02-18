package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "Помощь";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(getChatId(update), "Текст с помощью по боту");
    }
}
