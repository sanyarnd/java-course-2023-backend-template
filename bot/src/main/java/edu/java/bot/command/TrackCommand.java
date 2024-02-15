package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.UserChatRepository;
import edu.java.bot.service.LinkParsingProcessor;
import edu.java.bot.util.LinkValidator;
import edu.java.bot.website.WebsiteInfo;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import java.net.URI;

@Component
@RequiredArgsConstructor
public class TrackCommand implements Command {
    private final CommandInfo commandInfo = CommandInfo.TRACK;

    private final LinkValidator linkValidator;

    private final LinkParsingProcessor parsingProcessor;

    private final UserChatRepository userChatRepository;

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String INCORRECT_COMMAND_FORMAT_MESSAGE =
        "Неверный формат команды. " + CommandInfo.HELP.getType();

    private static final String LINK_ALREADY_ADDED_MESSAGE =
        "Данная ссылка уже добавлена. " + CommandInfo.LIST.getType();

    private static final String INVALID_LINK_MESSAGE = "Некорректная ссылка.";

    private static final String LINK_SUCCESSFULLY_ADDED_MESSAGE =
        "Ссылка успешно добавлена к отслеживанию. " + CommandInfo.LIST.getType();

    private static final String TITLE_OF_UNSUPPORTED_SERVICE_MESSAGE_WITH_LIST_OF_SUPPORTED =
        "Данный сервис не поддерживается :(\nПоддерживаемые сервисы приведены ниже:";

    @Override
    public SendMessage processCommand(Update update) {
        StringBuilder botMessage = new StringBuilder();

        Long chatId = update.message().chat().id();
        String userMessage = update.message().text();
        String[] userMessageParts = userMessage.split("\s+", 2);

        if (userMessageParts.length != 2) {
            botMessage.append(INCORRECT_COMMAND_FORMAT_MESSAGE);
            LOGGER.warn("ChatID: %d; incorrect command format: %s"
                .formatted(chatId, userMessage));

        } else {
            String link = userMessageParts[1];

            if (userChatRepository.containsLink(chatId, link)) {
                botMessage.append(LINK_ALREADY_ADDED_MESSAGE);
                LOGGER.warn("ChatID: %d; link %s has already added".formatted(chatId, link));

            } else {
                URI uri = linkValidator.validateLinkAndGetURI(link);
                if (uri == null) {
                    botMessage.append(INVALID_LINK_MESSAGE);
                    LOGGER.warn("ChatID: %d; invalid link: %s".formatted(chatId, link));

                } else if (parsingProcessor.processParsing(uri)) {
                    userChatRepository.addLink(chatId, uri.toString());
                    botMessage.append(LINK_SUCCESSFULLY_ADDED_MESSAGE);
                    LOGGER.info("ChatID: %d; link %s successfully added".formatted(chatId, link));

                } else {
                    botMessage.append(TITLE_OF_UNSUPPORTED_SERVICE_MESSAGE_WITH_LIST_OF_SUPPORTED);
                    for (var website : WebsiteInfo.values()) {
                        botMessage.append("\n").append(website.getDomain());
                    }
                    LOGGER.warn("ChatID: %d; link to unsupported webservice: %s".formatted(chatId, link));
                }
            }
        }
        return new SendMessage(chatId, botMessage.toString());
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
