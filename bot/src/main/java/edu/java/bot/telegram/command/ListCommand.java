package edu.java.bot.telegram.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {
    private final TelegramBot telegramBot;

    @Autowired
    public ListCommand(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void processCommand(Update update) {
        String message =
            "Список отслеживаемых ссылок пуст. Для добавления ссылки используйте " + CommandType.TRACK.getType();
        telegramBot.execute(new SendMessage(update.message().chat().id(), message));
    }

    @Override
    public String type() {
        return CommandType.LIST.getType();
    }

    @Override
    public String description() {
        return CommandType.LIST.getDescription();
    }
}
