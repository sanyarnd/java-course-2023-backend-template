package edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {
    private final CommandInfo commandInfo = CommandInfo.LIST;

    @Override
    public SendMessage processCommand(Update update) {
        // TODO
        String message =
            "Список отслеживаемых ссылок пуст. Для добавления ссылки используйте " + CommandInfo.TRACK.getType();
        return new SendMessage(update.message().chat().id(), message);
    }

    @Override
    public String type() {
        return commandInfo.getType();
    }

    @Override
    public String description() {
        return commandInfo.getDescription();
    }
}
