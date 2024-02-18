package edu.java.bot.util;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.util.action.ActionEnum;
import edu.java.bot.util.action.ActionFacade;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Bot implements AutoCloseable, UpdatesListener {
    private static final Logger LOGGER = LogManager.getLogger();
    private final String token;
    private TelegramBot bot;
    private ActionFacade facade;

    public Bot(ApplicationConfig config) {
        this.token = config.telegramToken();
    }

    @Override
    public int process(List<Update> list) {
        /*
         * TODO
         * Придумать, как защититься от отказов системы
         * Например, на одном сообщении все сломалось - что делать дальше
         */
        for (Update update: list) {
            LOGGER.debug("Bot get update");

            try {
                var response = facade.apply(update);
                // Отправка сообщения
                bot.execute(response.request());
                // Отправка меню команд
                bot.execute(response.menu());
            } catch (Throwable throwable) {
                LOGGER.error(throwable.toString());
                LOGGER.error(Arrays.toString(throwable.getStackTrace()));
            }
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void close() throws Exception {
        bot.shutdown();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void start() {
        bot = new TelegramBot(token);
        bot.setUpdatesListener(this);

        LOGGER.debug("Bot started");

        var commands = Arrays.stream(ActionEnum.values()).map(ActionEnum::getAction).toList();
        facade = new ActionFacade(commands);
    }
}
