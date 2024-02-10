package edu.java.bot.telegram.command;

import java.util.List;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    private final List<Command> commands;
    private final CommandInfo commandInfo = CommandInfo.HELP;

    @Autowired
    public HelpCommand(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public SendMessage processCommand(Update update) {
        StringBuilder message = new StringBuilder();
        message.append("Список поддерживаемых команд:").append("\n");

        for (var command: commands) {
            if (!command.type().equals(CommandInfo.START.getType())
                && !command.type().equals(this.type())) {
                message.append(command.type()).append(" - ")
                    .append(command.description()).append("\n");
            }
        }

        return new SendMessage(update.message().chat().id(), message.toString());
    }

    @Override
    public String type() {
        return commandInfo.getType();
    }

    @Override
    public String description() {
        return commandInfo.getDescription();
    }
}
