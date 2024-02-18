package edu.java.bot.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import edu.java.bot.bot.Bot;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class CommandService {
    private static final String UNDEFIENED_COMMAND_RESPONSE = "Извините, но данная" +
        " комманда не поддерживается, для вывода списка доступных комманд" +
        " введите /help";



    private ICommand[] commands;

    @Autowired
    public CommandService(ICommand[] commands) {
        this.commands = commands;
    }
    public boolean processCommand(Bot bot, Update update) {

        String chatId = update.message().chat().id().toString();
        String text = update.message().text();
        for (ICommand command: commands) {
            if(command.getName().equals(text)) {
                return command.processCommand(bot, update);
            }
        }
        return processUndefinedCommand(bot, update);
    }

    private boolean processUndefinedCommand(Bot bot, Update update) {
        return bot.writeToUser(update, UNDEFIENED_COMMAND_RESPONSE);
    }
}
