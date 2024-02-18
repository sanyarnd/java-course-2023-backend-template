package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.HelpCommand;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.commands.UntrackCommand;
import edu.java.bot.dao.MapStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class BotConfiguration {

    @Bean
    TelegramBot telegramBot(ApplicationConfig applicationConfig) {
        return new TelegramBot(applicationConfig.telegramToken());
    }

    @Bean
    Command startCommand(MapStorage mapStorage) {
        return new StartCommand(mapStorage);
    }

    @Bean
    Command truckCommand(MapStorage mapStorage) {
        return new TrackCommand(mapStorage);
    }

    @Bean
    Command untruckCommand(MapStorage mapStorage) {
        return new UntrackCommand(mapStorage);
    }

    @Bean
    Command listCommand(MapStorage mapStorage) {
        return new ListCommand(mapStorage);
    }

    @Bean
    Command helpCommand(List<Command> commandList) {
        return new HelpCommand(commandList);
    }

}
