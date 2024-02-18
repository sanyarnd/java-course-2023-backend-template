package edu.java.bot.strategy;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.BotState;
import edu.java.bot.exceptions.ResolvingException;
import edu.java.bot.exceptions.UserNotRegisteredException;
import edu.java.bot.service.ChatService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TrackCommandStrategy implements Strategy {
    final ChatService chatService;

    @Override
    public String resolve(Update update) throws ResolvingException {
        Long chatId = update.message().chat().id();
        if (!chatService.isRegistered(chatId)) {
            throw new UserNotRegisteredException();
        }

        if (update.message().text().equals("/track")) {
            chatService.updateBotState(chatId, BotState.TRACK);
            return "Please enter url you want to track";
        } else {
            chatService.trackUrl(chatId, update.message().text());
            chatService.updateBotState(chatId, BotState.READY);

            return "Url successfully tracked";

        }
    }
}
