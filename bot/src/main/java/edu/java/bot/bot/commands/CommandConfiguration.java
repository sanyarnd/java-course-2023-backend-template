package edu.java.bot.bot.commands;

import edu.java.bot.bot.commands.impl.HelpCommand;
import edu.java.bot.bot.commands.impl.ListCommand;
import edu.java.bot.bot.commands.impl.StartCommand;
import edu.java.bot.bot.commands.impl.TrackCommand;
import edu.java.bot.bot.commands.impl.UntrackCommand;
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
