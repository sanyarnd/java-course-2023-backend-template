package edu.java.bot.strategy;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.TelegramApplication;
import edu.java.bot.exceptions.ResolvingException;
import edu.java.bot.exceptions.UndefinedCommandException;

//изначальная точка входа любого запроса
public class InitialStrategy implements Strategy{
    static InitialStrategy instance = null;

    private InitialStrategy() {}

    static public Strategy getInstance() {
        if (instance == null) instance = new InitialStrategy();
        return instance;
    }

    @Override
    public String resolve(Update update) {
        try {
            Strategy strategy = switch (update.message().text()) {
                case "/start" -> StartCommandStrategy.getInstance();
                case "/help"  -> HelpCommandStrategy.getInstance();
                case "/list"  -> ListCommandStrategy.getInstance();
                case "/track" -> TrackCommandStrategy.getInstance();
                case "/untrack" -> UntrackCommandStrategy.getInstance();
                default ->
                    switch (TelegramApplication.state) {
                        case TRACK -> TrackCommandStrategy.getInstance();
                        case UNTRACK -> UntrackCommandStrategy.getInstance();
                        default ->
                            throw new UndefinedCommandException();
                    };

            };
            return strategy.resolve(update);
        }
        catch (ResolvingException e) {
            return switch (e) {
                case UndefinedCommandException _ ->
                     "you've sent undefined command! please, type /help to examine list of avaialbe commands";
                default -> "unexpected event, please contact to devs";
            };
        }
    }

}
