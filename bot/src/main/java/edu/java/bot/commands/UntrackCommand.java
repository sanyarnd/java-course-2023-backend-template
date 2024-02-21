package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.user.UserService;
import edu.java.bot.user.UserState;
import edu.java.bot.utils.LinkStorageService;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand implements Command {

    private final LinkStorageService linkStorageService;
    private final UserService userService;

    private static final String COMMAND = "/untrack";
    private static final String DESCRIPTION = "Удалить указанную ссылку или все ссылки.";
    private static final String REQUEST_LINK_MESSAGE = "Пожалуйста, укажите ссылку или 'all' для удаления.";
    private static final String ALL_LINKS_REMOVED_MESSAGE = "Все ссылки удалены.";
    private static final String LINK_REMOVED_SUCCESS_MESSAGE = "Ссылка успешно удалена.";
    private static final String LINK_NOT_FOUND_MESSAGE = "Указанная ссылка не найдена.";
    private static final String UNKNOWN_COMMAND = "Неизвестная команда.";

    public UntrackCommand(LinkStorageService linkStorageService, UserService userService) {
        this.linkStorageService = linkStorageService;
        this.userService = userService;
    }

    @Override
    public String command() {
        return COMMAND;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }

    @Override
    public SendMessage handle(Update update) {
        Long userId = update.message().from().id();
        UserState state = userService.getUserState(userId);
        Long chatId = update.message().chat().id();

        if (state == UserState.NONE) {
            userService.setUserState(userId, UserState.AWAITING_UNTRACK_LINK);
            return new SendMessage(chatId, REQUEST_LINK_MESSAGE);
        } else if (state == UserState.AWAITING_UNTRACK_LINK) {
            String messageText = update.message().text().trim();
            userService.setUserState(userId, UserState.NONE);

            if ("all".equalsIgnoreCase(messageText)) {
                linkStorageService.removeAllLinks(userId);
                return new SendMessage(chatId, ALL_LINKS_REMOVED_MESSAGE);
            } else {
                boolean isRemoved = linkStorageService.removeLink(userId, messageText);
                return new SendMessage(chatId, isRemoved ? LINK_REMOVED_SUCCESS_MESSAGE : LINK_NOT_FOUND_MESSAGE);
            }
        } else {
            return new SendMessage(chatId, UNKNOWN_COMMAND);
        }
    }

    @Override
    public boolean supports(Update update, UserService userService) {
        Long userId = update.message().from().id();
        UserState state = userService.getUserState(userId);
        String messageText = update.message().text();

        boolean isUntrackCommand = COMMAND.equals(messageText);
        boolean isUserAwaitingUntrackLink = state == UserState.AWAITING_UNTRACK_LINK;

        return isUntrackCommand || isUserAwaitingUntrackLink;
    }
}

