package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.util.LoggerQualifier;
import java.util.logging.Logger;
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

    @Bean
    @LoggerQualifier("TG_EXCEPTION_HANDLER")
    public Logger provideExceptionHandlerLogger() {
        return Logger.getLogger("TG_EXCEPTION_HANDLER");
    }
}
