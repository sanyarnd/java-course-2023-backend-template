package edu.java.bot.telegram.message;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.telegram.command.Command;
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

        // TODO
        return new SendMessage(update.message().chat().id(), "Такая команда не поддерживается :(");
    }
}
