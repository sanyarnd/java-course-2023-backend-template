package edu.java.bot.telegram;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.model.UserMessage;
import org.springframework.stereotype.Service;

public interface TelegramRequestService {

    void processMessage(UserMessage message);
}
