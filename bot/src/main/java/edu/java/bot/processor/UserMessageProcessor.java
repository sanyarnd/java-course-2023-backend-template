package edu.java.bot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;

@Component
public class UserMessageProcessor implements IUserMessageProcessor {

    private final List<Command> commandList;

    private static final String WRONG_COMMAND = "Wrong command! Please try again.";

    public UserMessageProcessor(List<Command> commandList) {
        this.commandList = Collections.unmodifiableList(commandList);
    }

    @Override
    public List<Command> commands() {
        return commandList;
    }

    @Override
    public SendMessage process(Update update) {
        Command command = commands()
            .stream()
            .filter(c -> c.supports(update))
            .findFirst()
            .orElse(null);

        if (command == null) {
            return new SendMessage(
                update.message().chat().id(),
                WRONG_COMMAND
            );
        }
        return command.handle(update);
    }
}
