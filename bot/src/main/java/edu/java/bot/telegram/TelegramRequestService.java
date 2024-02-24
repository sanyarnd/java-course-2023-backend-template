package edu.java.bot.telegram;

import edu.java.bot.model.UserMessage;

public interface TelegramRequestService {

    void processMessage(UserMessage message);
}
