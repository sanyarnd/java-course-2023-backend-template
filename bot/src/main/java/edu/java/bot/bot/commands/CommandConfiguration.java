package edu.java.bot.bot.commands;

import edu.java.bot.bot.commands.available.HelpCommand;
import edu.java.bot.bot.commands.available.ListCommand;
import edu.java.bot.bot.commands.available.StartCommand;
import edu.java.bot.bot.commands.available.TrackCommand;
import edu.java.bot.bot.commands.available.UntrackCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandConfiguration {
    @Bean
    public Command createHelpCommand() {
        return new HelpCommand();
    }

    @Bean
    public Command createListCommand() {
        return new ListCommand();
    }

    @Bean
    public Command createStartCommand() {
        return new StartCommand();
    }

    @Bean
    public Command createTrackCommand() {
        return new TrackCommand();
    }

    @Bean
    public Command createUntrackCommand() {
        return new UntrackCommand();
    }
}
