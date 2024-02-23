package edu.java.bot.commands_support;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Command;
import org.springframework.stereotype.Component;

@Component
public class UnknownCommand implements Command {

    @Override
    public String command() {
        return "/unknown";
    }

    @Override
    public String description() {
        return "Unknown command";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(
            update.message().chat().id(),
            "Unknown command. Use /help to see the available commands"
        );
    }
}
