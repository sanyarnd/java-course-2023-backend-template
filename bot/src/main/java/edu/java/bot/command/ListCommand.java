package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.UserChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ListCommand implements Command {
    private final CommandInfo commandInfo = CommandInfo.LIST;

    private final UserChatRepository userChatRepository;

    @Override
    public SendMessage processCommand(Update update) {
        StringBuilder message = new StringBuilder();

        List<String> trackingLinks = userChatRepository.getUserLinks(update.message().chat().id());
        if (trackingLinks == null || trackingLinks.isEmpty()) {
            message
                .append("Список отслеживаемых ссылок пуст. Для добавления ссылки используйте ")
                .append(CommandInfo.TRACK.getType());
        } else {
            message.append("Список отслеживаемых ссылок:");
            for (var link: trackingLinks) {
                message.append("\n").append(link);
            }
        }

        return new SendMessage(update.message().chat().id(), message.toString());
    }

    @Override
    public String type() {
        return commandInfo.getType();
    }

    @Override
    public String description() {
        return commandInfo.getDescription();
    }
}
