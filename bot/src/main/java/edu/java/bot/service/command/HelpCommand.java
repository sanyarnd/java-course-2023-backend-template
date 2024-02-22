package edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.util.CommonUtils;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand extends Command {
    private final List<Command> commands;

    public HelpCommand(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public String command() {
        return "help";
    }

    @Override
    public String description() {
        return "Show all possible commands";
    }

    @Override
    public SendMessage process(Update update) {
        return new SendMessage(
            update.message().chat().id(),
            "List of all possible commands:\n"
                + "1. " + this + "\n"
                + CommonUtils.joinEnumerated(commands, 2)
        );
    }
}
