package edu.java.bot.service.impl;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.comand.Command;
import edu.java.bot.service.UserMessageProcessor;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UserMessageProcessorImpl implements UserMessageProcessor {

    private final List<Command> commandList;

    public UserMessageProcessorImpl(List<Command> commandList) {
        this.commandList = commandList;
    }

    @Override
    public List<? extends Command> commands() {
        return commandList;
    }

    @Override
    public SendMessage process(Update update) {
        for (Command command : commandList) {
            if (command.supports(update)) {
                return command.handle(update);
            }
        }
        return new SendMessage(update.message().chat().id(), "Sorry, I don't understand this command.");
    }
}
