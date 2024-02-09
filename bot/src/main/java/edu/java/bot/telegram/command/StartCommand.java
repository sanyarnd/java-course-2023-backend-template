package edu.java.bot.telegram.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {
    private final TelegramBot telegramBot;

    @Autowired
    public StartCommand(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void processCommand(Update update) {
        // TODO

        String message = "Приветствую! Вы зарегистрировались в приложении Link Tracker!" + "\n"
            + "Чтобы вывести список доступных команд, используйте " + CommandType.HELP.getType();

        telegramBot.execute(new SendMessage(update.message().chat().id(), message));
    }

    @Override
    public String type() {
        return CommandType.START.getType();
    }

    @Override
    public String description() {
        return CommandType.START.getDescription();
    }
}
