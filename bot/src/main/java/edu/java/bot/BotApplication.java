package edu.java.bot;

import edu.java.bot.bot.ContentMateBot;
import edu.java.bot.configuration.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class BotApplication {
    public static void main(String[] args) {
        var context = SpringApplication.run(BotApplication.class, args);

        var contentMateBot = context.getBean(ContentMateBot.class);
        contentMateBot.start();
    }
}
