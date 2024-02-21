package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.user.UserService;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {

    private final UserService userService;

    private static final String COMMAND = "/start";
    private static final String DESCRIPTION = "Регистрация пользователя";
    private static final String SUCCESS_MESSAGE =
        "Вы успешно зарегистрированы. Теперь вы можете использовать все команды.";

    public StartCommand(UserService userService) {
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
        userService.registerUser(userId);
        return new SendMessage(update.message().chat().id(), SUCCESS_MESSAGE);
    }

    @Override
    public boolean supports(Update update, UserService userService) {
        if (update.message() != null && update.message().text() != null) {
            String messageText = update.message().text();
            Long userId = update.message().from().id();
            boolean isCommandMatch = messageText.startsWith(command());
            boolean isUserRegistered = userService.isRegistered(userId) || command().equals(COMMAND);

            return isCommandMatch && isUserRegistered;
        }
        return false;
    }
}
