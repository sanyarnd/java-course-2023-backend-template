package edu.java.bot.strategy;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.exceptions.ResolvingException;
import edu.java.bot.exceptions.UndefinedCommandException;
import edu.java.bot.exceptions.UserNotRegisteredException;
import edu.java.bot.service.ChatService;
import edu.java.bot.strategy.factory.StrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

//изначальная точка входа любого запроса
@RequiredArgsConstructor
public class InitialStrategy implements Strategy {
    @Autowired
    StrategyFactory strategyFactory;
    final ChatService chatService;

    @Override
    public String resolve(Update update) {
        try {
            Strategy strategy = switch (update.message().text()) {
                case "/start" -> strategyFactory.getStartCommandStrategyInstance();
                case "/help" -> strategyFactory.getHelpCommandStrategyInstance();
                case "/list" -> strategyFactory.getListCommandStrategyInstance();
                case "/track" -> strategyFactory.getTrackCommandStrategyInstance();
                case "/untrack" -> strategyFactory.getUntrackCommandStrategyInstance();
                default -> {
                    Long chatId = update.message().chat().id();
                    if (!chatService.isRegistered(chatId)) {
                        throw new UndefinedCommandException();
                    }

                    yield switch (chatService.findChat(chatId).get().botState) {
                        case TRACK -> strategyFactory.getTrackCommandStrategyInstance();
                        case UNTRACK -> strategyFactory.getUntrackCommandStrategyInstance();
                        default -> throw new UndefinedCommandException();
                    };
                }
            };
            return strategy.resolve(update);
        } catch (ResolvingException e) {
            return switch (e) {
                case UndefinedCommandException x ->
                    "you've sent undefined command! please, type /help to examine list of available commands";
                case UserNotRegisteredException x -> "You should register first using /start command";

                default -> "unexpected event, please contact to devs";
            };
        }
    }

}
