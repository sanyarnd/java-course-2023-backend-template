package edu.java.bot.strategy.factory;

import edu.java.bot.service.ChatService;
import edu.java.bot.strategy.HelpCommandStrategy;
import edu.java.bot.strategy.InitialStrategy;
import edu.java.bot.strategy.ListCommandStrategy;
import edu.java.bot.strategy.StartCommandStrategy;
import edu.java.bot.strategy.TrackCommandStrategy;
import edu.java.bot.strategy.UntrackCommandStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StrategyFactory {
    @Autowired
    ChatService chatService;

    @Bean
    public HelpCommandStrategy getHelpCommandStrategyInstance() {
        return new HelpCommandStrategy();
    }

    @Bean
    public StartCommandStrategy getStartCommandStrategyInstance() {
        return new StartCommandStrategy(chatService);
    }

    @Bean
    public InitialStrategy getInitialStrategyInstance() {
        return new InitialStrategy(chatService);
    }

    @Bean
    public ListCommandStrategy getListCommandStrategyInstance() {
        return new ListCommandStrategy(chatService);
    }

    @Bean
    public UntrackCommandStrategy getUntrackCommandStrategyInstance() {
        return new UntrackCommandStrategy(chatService);
    }

    @Bean
    public TrackCommandStrategy getTrackCommandStrategyInstance() {
        return new TrackCommandStrategy(chatService);
    }
}
