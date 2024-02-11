package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.model.UserChat;
import edu.java.bot.repository.UserChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command {
    private final CommandInfo commandInfo = CommandInfo.START;
    private final UserChatRepository userChatRepository;

    @Override
    public SendMessage processCommand(Update update) {
        StringBuilder message = new StringBuilder();

        Long chatId = update.message().chat().id();
        if (userChatRepository.findChat(chatId) == null) {
            message.append("Приветствую! Вы зарегистрировались в приложении Link Tracker!");
            userChatRepository.register(new UserChat(chatId, new ArrayList<>()));
        }
        else {
            message.append("Вы уже зарегистрированы :)");
        }
        message
            .append("\n").append("Чтобы вывести список доступных команд, используйте ")
            .append(CommandInfo.HELP.getType());

        return new SendMessage(chatId, message.toString());
    }

    @Override
    public String type() {
        return commandInfo.getType();
    }

    @Override
    public String description() {
        return CommandInfo.START.getDescription();
    }
}
