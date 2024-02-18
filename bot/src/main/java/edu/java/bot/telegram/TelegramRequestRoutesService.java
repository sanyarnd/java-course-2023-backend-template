package edu.java.bot.telegram;

import edu.java.bot.model.TelegramAnswer;
import edu.java.bot.model.UserMessage;

public interface TelegramRequestRoutesService {
    TelegramAnswer start(UserMessage message);

    TelegramAnswer help(UserMessage message);

    TelegramAnswer track(UserMessage message);

    TelegramAnswer untrack(UserMessage message);

    TelegramAnswer list(UserMessage message);

}
