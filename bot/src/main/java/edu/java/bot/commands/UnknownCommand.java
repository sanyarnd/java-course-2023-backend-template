package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class UnknownCommand implements Command {

    private static final String COMMAND = "/unknown";
    private static final String DESCRIPTION = "Неизвестная команда";
    private static final String RESPONSE_TEXT =
        "Такой команды не существует. Введите /help для списка доступных команд.";

    @Override
    public String command() {
        return COMMAND;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), RESPONSE_TEXT);
    }
}

