package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand implements Command {
    private final CommandInfo commandInfo = CommandInfo.UNTRACK;

    @Override
    public SendMessage processCommand(Update update) {
        // TODO
        return new SendMessage(update.message().chat().id(), "test message command /untrack");
    }

    @Override
    public String type() {
        return commandInfo.getType();
    }

    @Override
    public String description() {
        return CommandInfo.UNTRACK.getDescription();
    }
}
