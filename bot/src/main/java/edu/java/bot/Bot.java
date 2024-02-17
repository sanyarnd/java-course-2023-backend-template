package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.commands.Commands;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.responses.Responder;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class Bot implements UpdatesListener {

    final ApplicationConfig config;
    final Responder responder;
    final Commands commands;

    final TelegramBot bot;

    public Bot(@NotNull ApplicationConfig config, Responder responder, Commands commands) {
        this.config = config;
        this.responder = responder;
        this.commands = commands;
        this.bot = new TelegramBot(config.telegramToken());
        start();
    }

    void start() {
        bot.execute(new SetMyCommands(commands.asArray()));
        bot.setUpdatesListener(this, e -> System.err.println(e.response().toString()));
    }

    @Override
    public int process(@NotNull List<Update> list) {
        list.forEach(update -> {
            if (update.message() == null) {
                System.err.println(update);
                return;
            }
            bot.execute(responder.process(update));
        });
        return CONFIRMED_UPDATES_ALL;
    }
}
