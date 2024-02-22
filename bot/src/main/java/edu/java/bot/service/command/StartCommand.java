package edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.domain.Chat;
import edu.java.bot.service.ChatService;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class StartCommand extends Command {
    private final ChatService chatService;

    public StartCommand(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public String command() {
        return "start";
    }

    @Override
    public String description() {
        return "Start working with the bot";
    }

    @Override
    public SendMessage process(Update update) {
        Optional<Chat> optionalChat = chatService.find(update.message().chat().id());
        if (optionalChat.isEmpty()) {
            chatService.register(update.message().chat().id());
            return new SendMessage(update.message().chat().id(), "Hello! Welcome to our bot!");
        }
        return new SendMessage(update.message().chat().id(),
            "You are already working with our bot. Use /help to see a list of all possible commands");
    }
}
