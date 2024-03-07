package edu.java.bot.services;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.data.UsersWaiting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandService {
    private static final String UNDEFIENED_COMMAND_RESPONSE = "Извините, но данная"
        + " комманда не поддерживается, для вывода списка доступных комманд"
        + " введите /help";

    private ICommand[] commands;
    private UsersWaiting waitings;

    @Autowired
    public CommandService(ICommand[] commands, UsersWaiting waitings) {
        this.commands = commands;
        this.waitings = waitings;
    }

    public String processCommand(Update update) {
        String requestToUser = null;
        requestToUser = checkWaiting(update);
        if (requestToUser != null) {
            return requestToUser;
        }

        String commandNameFromUser = update.message().text();
        String textToUser = executeCommand(commandNameFromUser, update);
        if (textToUser != null) {
            return textToUser;
        }
        return processUndefinedCommand(update);
    }

    private String checkWaiting(Update update) {
        Long chatId = update.message().chat().id();
        String waitingName = waitings.getWaiting(chatId);
        if (!waitings.getDefaultWaiting().equals(waitingName)) {
            return executeCommand(waitingName, update);
        }
        return null;
    }

    private String processUndefinedCommand(Update update) {
        return UNDEFIENED_COMMAND_RESPONSE;
    }

    private String executeCommand(String commandName, Update update) {
        for (ICommand command : commands) {
            if (command.getName().equals(commandName)) {
                return command.processCommand(update);
            }
        }
        return null;
    }
}
