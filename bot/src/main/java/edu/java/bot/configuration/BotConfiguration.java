package edu.java.bot.configuration;

import edu.java.bot.commands.Command;
import edu.java.bot.commands.HelpCommand;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.commands.UntrackCommand;
import edu.java.bot.services.tracking.TrackingService;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfiguration {

    public final TrackingService trackingService;

    public BotConfiguration(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @Bean
    public List<? extends Command> commands() {
        return List.of(
            new StartCommand(),
            new HelpCommand(),
            new TrackCommand(trackingService),
            new ListCommand(trackingService),
            new UntrackCommand(trackingService)
        );
    }
}
