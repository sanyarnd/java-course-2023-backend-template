package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.user.UserService;
import edu.java.bot.user.UserState;
import edu.java.bot.utils.LinkStorageService;
import java.util.Set;
import java.util.StringJoiner;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {

    private final LinkStorageService linkStorageService;

    private static final String COMMAND = "/list";
    private static final String DESCRIPTION = "Вывести все отслеживаемые ссылки.";
    private static final String EMPTY_LINKS_MESSAGE = "На данный момент вы не отслеживаете никакие ссылки.";
    private static final String TRACKED_LINKS_MESSAGE_TITLE = "Ваши отслеживаемые ссылки:";

    public ListCommand(LinkStorageService linkStorageService) {
        this.linkStorageService = linkStorageService;
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
        Set<String> links = linkStorageService.getLinks(userId);

        if (links.isEmpty()) {
            return new SendMessage(update.message().chat().id(), EMPTY_LINKS_MESSAGE);
        } else {
            StringJoiner message = new StringJoiner("\n", TRACKED_LINKS_MESSAGE_TITLE + "\n", "");
            links.forEach(message::add);
            return new SendMessage(update.message().chat().id(), message.toString());
        }
    }

    @Override
    public boolean supports(Update update, UserService userService) {
        if (update.message() != null && update.message().text() != null) {
            Long userId = update.message().from().id();
            UserState userState = userService.getUserState(userId);
            return COMMAND.equals(update.message().text()) && userState == UserState.NONE;
        }
        return false;
    }
}
