package edu.java.bot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.Command;
import edu.java.bot.command.CommandInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserMessageProcessor {
    private final Map<String, Command> commands;

    @Autowired
    public UserMessageProcessor(List<Command> commands) {
        this.commands = new HashMap<>();

        for (var command : commands) {
            this.commands.put(command.type(), command);
        }
    }

    public SendMessage processUpdate(Update update) {
        String commandType = update.message().text().split("\s+")[0];
        Command command = commands.get(commandType);

        if (command != null) {
            return command.processCommand(update);
        }
        String errorMessage = "Такая команда не поддерживается :(" + "\n"
            + "Чтобы вывести список доступных команд, используйте " + CommandInfo.HELP.getType();
        return new SendMessage(update.message().chat().id(), errorMessage);
    }
}
