package edu.java.bot.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.services.CommandService;
import edu.java.bot.services.ICommand;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//Is it Controller?
@Component
public class Bot {
    private static final Logger LOGGER = Logger.getLogger(Bot.class.getName());
    private final TelegramBot telegramBot;
    private final ApplicationConfig applicationConfig;
    private final CommandService commandService;
    private final ICommand[] commands;

    @Autowired
    public Bot(ApplicationConfig applicationConfig, CommandService commandService, ICommand[] commands) {
        this.applicationConfig = applicationConfig;
        this.commandService = commandService;
        this.commands = commands;

        telegramBot = new TelegramBot(applicationConfig.telegramToken());

        addMenu();

        telegramBot.setUpdatesListener(updates -> {
            Integer lastId = UpdatesListener.CONFIRMED_UPDATES_NONE;
            for (Update update : updates) {
                if (!handleMessage(update)) {
                    return lastId;
                }
                lastId = update.updateId();
            }

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, e -> {
            if (e.response() != null) {
                e.response().errorCode();
                e.response().description();
            }
        });
    }

    private void addMenu() {
        BotCommand[] commandsForMenu = Arrays.stream(commands)
            .map(command -> new BotCommand(command.getName(), command.getDescription()))
            .toArray(BotCommand[]::new);

        SetMyCommands setCommands = new SetMyCommands(commandsForMenu);
        BaseResponse response = telegramBot.execute(setCommands);
        //TODO:: implement some actions if response status is bad
    }

    private boolean handleMessage(Update update) {
        if (update == null || update.message() == null || update.message().chat() == null) {
            return true;
        }

        String requestForUser = null;
        try {
            requestForUser = commandService.processCommand(update);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return false;
        }
        if (requestForUser != null) {
            writeToUser(update, requestForUser);
        }
        return true;
    }

    public void writeToUser(Update update, String text) {
        SendMessage request = new SendMessage(update.message().chat().id(), text);
        telegramBot.execute(request);
    }

    public void callUsers(String url, String description, List<Long> tgChatIds) {
        String updateMessage = getUpdateMessage(url, description);
        for (Long userId : tgChatIds) {
            SendMessage request = new SendMessage(userId, updateMessage);
            telegramBot.execute(request);
        }
    }

    private String getUpdateMessage(String url, String description) {
        //TODO:: how should update message look like?
        return "Обновление на " + url + "\nОписание: " + description;
    }
}
