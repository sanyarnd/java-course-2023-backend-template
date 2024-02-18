package edu.java.bot.controllers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.services.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Controller
public class Bot {
    private final TelegramBot telegramBot;
    private final ApplicationConfig applicationConfig;
    private final CommandService commandService;

    @Autowired
    public Bot(ApplicationConfig applicationConfig, CommandService commandService) {
        this.applicationConfig = applicationConfig;
        this.commandService = commandService;
        telegramBot = new TelegramBot(applicationConfig.telegramToken());
        telegramBot.setUpdatesListener(updates -> {
            Integer lastId = UpdatesListener.CONFIRMED_UPDATES_NONE;
            for (Update update : updates) {
                if (!handleMessage(update)) {
                    return lastId;
                }
                lastId = update.updateId();
            }

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
        this.commandService.setBot(telegramBot);
    }

    public boolean handleMessage(Update update) {
        if (update.message() == null || update.message().chat() == null) {
            return true;
        }
        System.out.println(update.message().text());
        return commandService.processCommand(update);
    }
}
