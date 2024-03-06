package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    private final List<Command> commands;
    private static final String SEPARATOR = " : ";
    private String text;

    private void createText() {
        StringBuilder sbText = new StringBuilder();
        addCommandsToText(sbText);
        text = sbText.toString();
    }

    private void addCommandsToText(StringBuilder sbText) {
        for (Command command : commands) {
            sbText.append(command.command()).append(SEPARATOR).append(command.description()).append("\n");
        }
        sbText.append(command()).append(SEPARATOR).append(description());
    }

    private String getText() {
        return text;
    }

    public HelpCommand(List<Command> commands) {
        this.commands = commands;
        createText();
    }

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "shows available commands";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), getText());
    }
}
