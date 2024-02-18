package edu.java.bot.services;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserMessageProcessor implements UserMessageProcessor {

    public final List<? extends Command> commands;

    @Autowired
    public DefaultUserMessageProcessor(List<? extends Command> commands) {

        this.commands = commands;
    }

    @Override
    public List<? extends Command> commands() {
        return commands;
    }

    @Override
    public SendMessage process(Update update) {
        for (Command command : commands()) {
            if (command.supports(update)) {
                return command.handle(update);
            }
        }
        if (update.message() != null) {
            return new SendMessage(update.message().chat().id(), "Неизвестная команда");
        }
        return null;
    }
}
