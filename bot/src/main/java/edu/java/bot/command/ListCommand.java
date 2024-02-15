package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.UserChatRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ListCommand implements Command {
    private final CommandInfo commandInfo = CommandInfo.LIST;

    private final UserChatRepository userChatRepository;

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String EMPTY_LINKS_LIST_MESSAGE =
        "Список отслеживаемых ссылок пуст. Для добавления ссылки используйте " + CommandInfo.TRACK.getType();

    private static final String LINKS_LIST_MESSAGE_TITLE = "Список отслеживаемых ссылок:";

    @Override
    public SendMessage processCommand(Update update) {
        StringBuilder botMessage = new StringBuilder();
        Long chatId = update.message().chat().id();

        List<String> trackingLinks = userChatRepository.getUserLinks(chatId);
        if (trackingLinks == null || trackingLinks.isEmpty()) {
            botMessage.append(EMPTY_LINKS_LIST_MESSAGE);
            LOGGER.info("ChatID: %d; command: %s; result: links list is empty".formatted(chatId, type()));
        } else {
            botMessage.append(LINKS_LIST_MESSAGE_TITLE);
            for (var link: trackingLinks) {
                botMessage.append("\n").append(link);
            }
            LOGGER.info("ChatID: %d; command: %s; result: links list has sent".formatted(chatId, type()));
        }

        return new SendMessage(update.message().chat().id(), botMessage.toString());
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
