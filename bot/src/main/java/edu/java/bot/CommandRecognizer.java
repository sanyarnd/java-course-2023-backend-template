package edu.java.bot;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.commands_support.UnknownCommand;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class CommandRecognizer {
    private final Command unknown = new UnknownCommand();

    private Map<String, Command> getCommandMap() {
        Map<String, Command> result = new HashMap<>();
        List<? extends Command> commands = UserMessageListener.commands();
        for (var command : commands) {
            result.put(command.command(), command);
        }
        return result;
    }

    public Command recognize(Update update) {
        var commandMap = getCommandMap();
        String messageText = update.message().text();
        Command recognizedCommand = commandMap.get(messageText);
        if (recognizedCommand == null) {
            recognizedCommand = unknown;
        }
        return recognizedCommand;
    }
}
