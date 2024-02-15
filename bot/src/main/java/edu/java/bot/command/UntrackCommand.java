package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.UserChatRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UntrackCommand implements Command {
    private final CommandInfo commandInfo = CommandInfo.UNTRACK;

    private final UserChatRepository userChatRepository;

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String INCORRECT_COMMAND_FORMAT_MESSAGE =
        "Неверный формат команды. " + CommandInfo.HELP.getType();

    private static final String SUCCESSFUL_LINK_REMOVAL_MESSAGE =
        "Сылка успешно удалена. " + CommandInfo.LIST.getType();

    private static final String NOT_FOUND_MESSAGE =
        "Ничего не найдено :( " + CommandInfo.LIST.getType();

    @Override
    public SendMessage processCommand(Update update) {
        String botMessage;

        Long chatId = update.message().chat().id();
        String userMessage = update.message().text();
        String[] userMessageParts = userMessage.split("\s+", 2);

        if (userMessageParts.length != 2) {
            botMessage = INCORRECT_COMMAND_FORMAT_MESSAGE;
            LOGGER.warn("ChatID: %d; incorrect command format: %s"
                .formatted(chatId, userMessage));
        } else {
            String link = userMessageParts[1];
            if (userChatRepository.containsLink(chatId, link)) {
                userChatRepository.removeLink(chatId, link);
                botMessage = SUCCESSFUL_LINK_REMOVAL_MESSAGE;
                LOGGER.info("ChatID: %d; link %s successfully removed".formatted(chatId, link));
            } else {
                botMessage = NOT_FOUND_MESSAGE;
                LOGGER.warn("ChatID: %d; link %s not found in tracked list".formatted(chatId, link));
            }
        }
        return new SendMessage(chatId, botMessage);
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
