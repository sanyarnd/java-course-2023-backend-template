package edu.java.bot.services;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.bot.Bot;
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

    public boolean processCommand(Bot bot, Update update) {

        Long chatId = update.message().chat().id();
        String text = update.message().text();

        String waitingName = waitings.getWaiting(chatId);
        if (!waitings.getDefaultWaiting().equals(waitingName)) {
            for (ICommand command : commands) {
                if (command.getName().equals(waitingName)) {
                    return command.processCommand(bot, update);
                }
            }
        }
        for (ICommand command : commands) {
            if (command.getName().equals(text)) {
                return command.processCommand(bot, update);
            }
        }
        return processUndefinedCommand(bot, update);
    }

    private boolean processUndefinedCommand(Bot bot, Update update) {
        return bot.writeToUser(update, UNDEFIENED_COMMAND_RESPONSE);
    }
}
