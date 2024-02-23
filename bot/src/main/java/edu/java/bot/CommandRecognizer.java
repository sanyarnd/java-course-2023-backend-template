package edu.java.bot;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.commands.Command;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandRecognizer {
    private final Map<String, Command> commands;

    @Autowired
    public CommandRecognizer(List<Command> commands) {
        this.commands = commands
            .stream()
            .collect(Collectors.toMap(Command::command, Function.identity()));
    }

    public Command recognize(Update update) {
        String messageText = update.message().text();
        Command recognizedCommand = commands.get(messageText);
        if (recognizedCommand == null) {
            recognizedCommand = commands.get("/unknown");
        }
        return recognizedCommand;
    }
}
