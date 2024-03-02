package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public String provideToken(ApplicationConfig applicationConfig) {
        return applicationConfig.telegramToken();
    }

    @Bean
    public TelegramBot provideBot(String token) {
        return new TelegramBot(token);
    }
}
