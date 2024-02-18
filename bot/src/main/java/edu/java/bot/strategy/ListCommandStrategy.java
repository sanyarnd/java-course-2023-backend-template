package edu.java.bot.strategy;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.exceptions.ResolvingException;
import edu.java.bot.exceptions.UserNotRegisteredException;
import edu.java.bot.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListCommandStrategy implements Strategy {
    final ChatService chatService;

    @Override
    public String resolve(Update update) throws ResolvingException {
        Long chatId = update.message().chat().id();
        if (!chatService.isRegistered(chatId)) {
            throw new UserNotRegisteredException();
        }
        return chatService.findChat(chatId)
            .map(x -> x.trackedSites.toString())
            .orElseThrow(ResolvingException::new);
    }
}
