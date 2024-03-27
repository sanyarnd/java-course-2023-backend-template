package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramBotConfig {

    private final ApplicationConfig applicationConfig;

    public TelegramBotConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Bean
    public TelegramBot telegramBot() {
        return new TelegramBot(applicationConfig.telegramToken());
    }
}
