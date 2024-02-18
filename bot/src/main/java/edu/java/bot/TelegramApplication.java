package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.strategy.factory.StrategyFactory;
import org.springframework.stereotype.Component;

@Component
public class TelegramApplication {

    StrategyFactory strategyFactory;

    ApplicationConfig applicationConfig;

    TelegramApplication(StrategyFactory strategyFactory, ApplicationConfig applicationConfig) {
        this.strategyFactory = strategyFactory;
        this.applicationConfig = applicationConfig;
        run(applicationConfig.telegramToken);
    }

    public void run(String token) {
        TelegramBot bot = new TelegramBot(token);

        bot.setUpdatesListener(updates -> {

            for (Update update : updates) {
                String response = strategyFactory.getInitialStrategyInstance().resolve(update);
                bot.execute(new SendMessage(update.message().chat().id(), response));

            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
// Create Exception Handler
        }, e -> {

        });
    }

}
