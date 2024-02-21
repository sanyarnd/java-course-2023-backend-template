package edu.java.bot.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.user.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandService {
    private final List<Command> commands;
    private final UserService userService;

    private static final String REGISTRATION_REQUEST_MESSAGE =
        "Пожалуйста, зарегистрируйтесь с помощью команды /start.";
    private static final String UNREGISTERED_COMMAND_NOT_FOUND_MESSAGE =
        "Команда не найдена. Для начала работы с ботом используйте /start.";
    private static final String REGISTERED_COMMAND_NOT_FOUND_MESSAGE =
        "Такой команды не существует, воспользуйтесь меню для просмотра доступных команд";

    @Autowired
    public CommandService(List<Command> commands, UserService userService) {
        this.commands = commands;
        this.userService = userService;
    }

    public void processUpdate(Update update, TelegramBot bot) {
        if (update.message() != null && update.message().text() != null) {
            Long userId = update.message().from().id();
            boolean isRegistered = userService.isRegistered(userId);
            SendMessage request = null;

            if (!isRegistered && !update.message().text().startsWith("/start")) {
                request = new SendMessage(update.message().chat().id(), REGISTRATION_REQUEST_MESSAGE);
            } else {
                Optional<Command> commandOptional = commands.stream()
                    .filter(command -> command.supports(update, userService))
                    .findFirst();
                if (commandOptional.isPresent()) {
                    request = commandOptional.get().handle(update);
                } else {
                    request = handleUnknownCommand(update, isRegistered);
                }
            }

            if (request != null) {
                bot.execute(request);
            }
        }
    }

    private SendMessage handleUnknownCommand(Update update, boolean isRegistered) {
        String messageText =
            isRegistered ? REGISTERED_COMMAND_NOT_FOUND_MESSAGE : UNREGISTERED_COMMAND_NOT_FOUND_MESSAGE;
        return new SendMessage(update.message().chat().id(), messageText);
    }
}
