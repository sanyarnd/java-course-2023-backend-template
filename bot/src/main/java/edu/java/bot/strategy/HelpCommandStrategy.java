package edu.java.bot.strategy;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.exceptions.ResolvingException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class HelpCommandStrategy implements Strategy {
    List<String> allowedCommands = new ArrayList<>();

    public HelpCommandStrategy() {
        allowedCommands.add("/help - prints list of all commands");
        allowedCommands.add("/start - registers you in the system");
        allowedCommands.add("/track - tracks one more site");
        allowedCommands.add("/untrack - untracks one more site");
        allowedCommands.add("/list - lists all sites");
    }

    @Override
    public String resolve(Update update) throws ResolvingException {
        return allowedCommands.toString();
    }

}
