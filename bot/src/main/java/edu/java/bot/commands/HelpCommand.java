package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {

    private final List<Command> commands;

    private static final String SUPPORTED_COMMANDS_MESSAGE_TITLE = "Список поддерживаемых команд:";
    private static final String DESCRIPTION_MESSAGE = "Показать список доступных команд.";

    @Autowired
    public HelpCommand(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return DESCRIPTION_MESSAGE;
    }

    @Override
    public SendMessage handle(Update update) {
        StringBuilder botMessage = new StringBuilder(SUPPORTED_COMMANDS_MESSAGE_TITLE);

        commands.forEach(command -> {
            if (!command.command().equals("/start") && !command.command().equals("/unknown")) {
                botMessage.append("\n").append(command.command()).append(" - ").append(command.description());
            }
        });

        return new SendMessage(update.message().chat().id(), botMessage.toString());
    }
}
