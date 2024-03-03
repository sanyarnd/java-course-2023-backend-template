package edu.java.bot.di;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.core.util.ApiQualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public TelegramBot provideBot(ApplicationConfig config) {
        return new TelegramBot(config.telegramToken());
    }

    @Bean
    @ApiQualifier("scrapper")
    public String provideScrapperEndpoint(ApplicationConfig config) {
        return config.scrapper();
    }
}
