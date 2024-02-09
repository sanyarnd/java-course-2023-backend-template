package edu.java.bot.telegram.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.telegram.command.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LinkBot implements Bot {
    private final TelegramBot telegramBot;
    private final Map<String, Command> commands;

    @Autowired
    public LinkBot(TelegramBot telegramBot, List<Command> commands) {
        this.telegramBot = telegramBot;
        this.telegramBot.setUpdatesListener(this::processUpdates);

        this.commands = new HashMap<>();
        for (var command : commands) {
            this.commands.put(command.type(), command);
        }
    }

    @Override
    public void processCurrentUpdate(Update update) {
        String userMessage = update.message().text();
        Command command = commands.get(userMessage.split("\s+")[0]);

        if (command != null) {
            command.processCommand(update);
        }
    }

    @Override
    public int processUpdates(List<Update> updates) {
        updates.forEach(this::processCurrentUpdate);
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
