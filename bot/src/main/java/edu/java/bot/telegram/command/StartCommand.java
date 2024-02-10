package edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {
    private final CommandInfo commandInfo = CommandInfo.START;

    @Override
    public SendMessage processCommand(Update update) {
        // TODO
        String message = "Приветствую! Вы зарегистрировались в приложении Link Tracker!" + "\n"
            + "Чтобы вывести список доступных команд, используйте " + CommandInfo.HELP.getType();

        return new SendMessage(update.message().chat().id(), message);
    }

    @Override
    public String type() {
        return commandInfo.getType();
    }

    @Override
    public String description() {
        return CommandInfo.START.getDescription();
    }
}
