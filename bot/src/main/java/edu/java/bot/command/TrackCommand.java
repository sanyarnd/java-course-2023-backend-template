package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.UserChatRepository;
import edu.java.bot.service.LinkParsingProcessor;
import edu.java.bot.util.LinkValidator;
import edu.java.bot.website.WebsiteInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.net.URI;

@Component
@RequiredArgsConstructor
public class TrackCommand implements Command {
    private final CommandInfo commandInfo = CommandInfo.TRACK;

    private final LinkValidator linkValidator;

    private final LinkParsingProcessor parsingProcessor;

    private final UserChatRepository userChatRepository;

    @Override
    public SendMessage processCommand(Update update) {
        StringBuilder messageForUser = new StringBuilder();

        Long chatId = update.message().chat().id();
        String[] userMessageParts = update.message().text().split("\s+");

        if (userMessageParts.length != 2) {
            messageForUser
                .append("Некорректный формат команды. Используйте следующий шаблон:").append("\n")
                .append(CommandInfo.TRACK.getType()).append(" <ссылка для отслеживания>");
        } else {
            URI uri = linkValidator.validateLinkAndGetURI(userMessageParts[1]);
            if (uri == null) {
                messageForUser.append("Некорректная ссылка");
            } else if (parsingProcessor.processParsing(uri)) {
                userChatRepository.addLink(chatId, uri.toString());
                messageForUser.append("Ссылка успешно добавлена к отслеживанию.");
            } else {
                messageForUser
                    .append("Данный сервис не поддерживается :(").append("\n")
                    .append("Поддерживаемые сервисы приведены ниже:");
                for (var website: WebsiteInfo.values()) {
                    messageForUser.append("\n").append(website.getDomain());
                }
            }
        }
        return new SendMessage(chatId, messageForUser.toString());
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
