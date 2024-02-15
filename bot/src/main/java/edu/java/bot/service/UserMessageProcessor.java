package edu.java.bot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.Command;
import edu.java.bot.command.CommandInfo;
import edu.java.bot.repository.UserChatRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserMessageProcessor {
    private final Map<String, Command> commands;

    private final UserChatRepository userChatRepository;

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String UNSUPPORTED_COMMAND_MESSAGE =
        "Такая команда не поддерживается :(\nЧтобы вывести список доступных команд, используйте "
            + CommandInfo.HELP.getType();

    private static final String NOT_REGISTERED_MESSAGE =
        "Сначала необходимо зарегистрироваться. " + CommandInfo.START.getType();

    @Autowired
    public UserMessageProcessor(List<Command> commands, UserChatRepository userChatRepository) {
        this.userChatRepository = userChatRepository;

        this.commands = new HashMap<>();
        for (var command : commands) {
            this.commands.put(command.type(), command);
        }
    }

    public SendMessage processUpdate(Update update) {
        String botMessage;
        String userMessage = update.message().text();
        String commandType = userMessage.split("\s+")[0];

        Long chatId = update.message().chat().id();
        LOGGER.info("ChatID: %d with message: %s".formatted(chatId, userMessage));

        if (userChatRepository.findChat(chatId) != null
            || commandType.equals(CommandInfo.START.getType())) {

            Command command = commands.get(commandType);
            if (command != null) {
                LOGGER.info("ChatID: %d; processing command: %s".formatted(chatId, command.type()));
                return command.processCommand(update);
            }

            LOGGER.error("ChatID: %d; unsupported command: %s".formatted(chatId, userMessage));
            botMessage = UNSUPPORTED_COMMAND_MESSAGE;
        } else {
            LOGGER.error("ChatID: %d unregistered; with message: %s".formatted(chatId, userMessage));
            botMessage = NOT_REGISTERED_MESSAGE;
        }

        return new SendMessage(chatId, botMessage);
    }
}
