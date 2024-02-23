package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.configuration.ApplicationConfig;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Bot implements UpdatesListener, AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    private UserMessageListener updateListener;
    private final TelegramBot bot;

    @Autowired
    public Bot(ApplicationConfig appCof, UserMessageListener updateListener) {
        this.bot = new TelegramBot(appCof.telegramToken());
        this.updateListener = updateListener;
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            bot.execute(updateListener.process(update));
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void start() {
        //bot.execute(new SetMyCommands(commands.toArray(new BotCommand[]{})).scope(new BotCommandsScopeChat(chatId)));
        bot.setUpdatesListener(
            this::process,
            e -> {
                if (e.response() != null) {
                    // got bad response from telegram
                    e.response().errorCode();
                    e.response().description();
                } else {
                    LOGGER.warn("probably network error");
                }
            }
        );
    }

    @Override
    public void close() {
        bot.shutdown();
    }
}
