package edu.java.bot.configuration;

import com.pengrad.telegrambot.model.BotCommand;
import edu.java.bot.command.CommandChain;
import edu.java.bot.command.CommandExecutor;
import edu.java.bot.command.HelpCommand;
import edu.java.bot.command.ListCommand;
import edu.java.bot.command.StartCommand;
import edu.java.bot.command.TrackCommand;
import edu.java.bot.command.UntrackCommand;
import edu.java.bot.service.CommandService;
import edu.java.bot.resolver.UpdateCallbackResolver;
import edu.java.bot.resolver.UpdateMessageResolver;
import edu.java.bot.resolver.UpdateResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static edu.java.bot.command.Command.HELP;
import static edu.java.bot.command.Command.LIST;
import static edu.java.bot.command.Command.TRACK;
import static edu.java.bot.command.Command.UNTRACK;

@Configuration
public class BotConfiguration {

    @Bean
    public CommandService linkService() {
        return new CommandService();
    }

    @Bean
    public UpdateResolver updateResolver() {
        return UpdateResolver.link(
            new UpdateMessageResolver(commandChain()),
            new UpdateCallbackResolver(linkService())
        );
    }

    @Bean
    public CommandChain commandChain() {
        return new CommandChain(
            CommandExecutor.link(
                new StartCommand(),
                new HelpCommand(),
                new ListCommand(linkService()),
                new TrackCommand(linkService()),
                new UntrackCommand(linkService())
            ));
    }

    @Bean
    public BotCommand[] commands() {
        return new BotCommand[] {
            new BotCommand(TRACK.getCommandName(), TRACK.getCommandDescription()),
            new BotCommand(UNTRACK.getCommandName(), UNTRACK.getCommandDescription()),
            new BotCommand(LIST.getCommandName(), LIST.getCommandDescription()),
            new BotCommand(HELP.getCommandName(), HELP.getCommandDescription())
        };
    }

    @Bean
    public String telegramToken(ApplicationConfig applicationConfig) {
        return applicationConfig.telegramToken();
    }
}
