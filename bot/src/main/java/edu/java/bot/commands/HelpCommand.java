package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.user.UserService;
import edu.java.bot.user.UserState;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {

    private final List<Command> commands;

    private static final String SUPPORTED_COMMANDS_MESSAGE_TITLE = "Список поддерживаемых команд:";
    private static final String DESCRIPTION_MESSAGE = "Показать список доступных команд.";
    private static final String COMMAND = "/help";

    @Autowired
    public HelpCommand(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public String command() {
        return COMMAND;
    }

    @Override
    public String description() {
        return DESCRIPTION_MESSAGE;
    }

    @Override
    public SendMessage handle(Update update) {
        StringBuilder botMessage = new StringBuilder(SUPPORTED_COMMANDS_MESSAGE_TITLE);

        commands.forEach(command -> {
            if (!command.command().equals("/start")) {
                botMessage.append("\n").append(command.command()).append(" - ").append(command.description());
            }
        });

        return new SendMessage(update.message().chat().id(), botMessage.toString());
    }

    @Override
    public boolean supports(Update update, UserService userService) {
        if (update.message() != null && update.message().text() != null) {
            String messageText = update.message().text();
            Long userId = update.message().from().id();
            UserState userState = userService.getUserState(userId);
            return COMMAND.equals(messageText) && userState == UserState.NONE;
        }
        return false;
    }
}
