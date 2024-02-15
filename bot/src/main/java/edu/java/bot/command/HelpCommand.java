package edu.java.bot.command;

import java.util.List;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.website.WebsiteInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HelpCommand implements Command {
    private final List<Command> commands;
    private final CommandInfo commandInfo = CommandInfo.HELP;

    private static final String SUPPORTED_COMMANDS_MESSAGE_TITLE = "Список поддерживаемых команд:";

    private static final String SUPPORTED_WEBSERVICES_MESSAGE_TITLE = "Ниже приведены поддерживаемые сервисы:";

    @Override
    public SendMessage processCommand(Update update) {
        StringBuilder botMessage = new StringBuilder();

        botMessage.append(SUPPORTED_COMMANDS_MESSAGE_TITLE).append("\n");
        for (var command: commands) {
            if (!command.type().equals(CommandInfo.START.getType())) {
                botMessage.append(command.type()).append(" - ").append(command.description()).append("\n");
            }
        }

        botMessage.append("\n").append(SUPPORTED_WEBSERVICES_MESSAGE_TITLE);
        for (var website: WebsiteInfo.values()) {
            botMessage.append("\n").append(website.getDomain());
        }

        return new SendMessage(update.message().chat().id(), botMessage.toString());
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
