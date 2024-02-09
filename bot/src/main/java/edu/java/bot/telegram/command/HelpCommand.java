package edu.java.bot.telegram.command;

import java.util.Arrays;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class HelpCommand implements Command {
    private final TelegramBot telegramBot;

    @Autowired
    public HelpCommand(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void processCommand(Update update) {
        StringBuilder message = new StringBuilder();
        message.append("Список поддерживаемых команд:").append("\n");

        Arrays.stream(CommandType.values()).forEach(
            value -> {
                if (!value.getType().equals(CommandType.START.getType())
                    && !(value.getType().equals(this.type()))) {
                    message.append(value.getType()).append(" - ")
                        .append(value.getDescription()).append("\n");
                }
            });

        telegramBot.execute(new SendMessage(update.message().chat().id(), message.toString()));
    }

    @Override
    public String type() {
        return CommandType.HELP.getType();
    }

    @Override
    public String description() {
        return CommandType.HELP.getDescription();
    }
}
