package edu.java.bot.strategy;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.exceptions.ResolvingException;
import edu.java.bot.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartCommandStrategy implements Strategy {
    final ChatService chatService;

    @Override
    public String resolve(Update update) throws ResolvingException {
        String responseMessage;
        Long chatId = update.message().chat().id();
        if (!chatService.isRegistered(chatId)) {
            chatService.register(chatId);
            responseMessage = "Hello! you've successfully registered. type /help to see all commands";
        } else {
            responseMessage = "Oh, you've been registered previously";
        }
        return responseMessage;
    }
}
