package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.commands.Command;
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
    private final List<Command> commands;

    @Autowired
    public Bot(ApplicationConfig appCof, UserMessageListener updateListener, List<Command> commands) {
        this.bot = new TelegramBot(appCof.telegramToken());
        this.updateListener = updateListener;
        this.commands = commands;
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            bot.execute(updateListener.process(update));
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void start() {
        bot.execute(setMenu());
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

    private SetMyCommands setMenu() {
        return new SetMyCommands(
            commands.stream()
                .filter(command -> !command.command().equals("/unknown"))
                .map(command -> new BotCommand(
                        command.command(),
                        command.description()
                    )
                ).toArray(BotCommand[]::new)
        );
    }

    @Override
    public void close() {
        bot.shutdown();
    }
}
