package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.UserChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UntrackCommand implements Command {
    private final CommandInfo commandInfo = CommandInfo.UNTRACK;

    private final UserChatRepository userChatRepository;

    @Override
    public SendMessage processCommand(Update update) {
        StringBuilder messageForUser = new StringBuilder();

        Long chatId = update.message().chat().id();
        String[] userMessageParts = update.message().text().split("\s+");

        if (userMessageParts.length != 2) {
            messageForUser
                .append("Некорректный формат команды. Используйте следующий шаблон:").append("\n")
                .append(CommandInfo.UNTRACK.getType()).append(" <ссылка, которую нужно удалить>");
        } else {
            String url = userMessageParts[1];
            if (userChatRepository.containsLink(chatId, url)) {
                userChatRepository.removeLink(chatId, url);
                messageForUser.append("Сылка ").append(url).append(" успешно удалена");
            } else {
                messageForUser
                    .append("Ссылка ").append(" не найдена среди отслеживаемых").append(".\n")
                    .append("Для получения отслеживаемых ссылок используйте команду ")
                    .append(CommandInfo.LIST.getType());
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
        return CommandInfo.UNTRACK.getDescription();
    }
}
