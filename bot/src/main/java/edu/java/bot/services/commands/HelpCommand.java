package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.services.ICommand;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements ICommand {
    private static final String HELP_COMMAND_RESPONSE = "start - Введите, чтобы начать\n"
        + "help - Вывести окно с командами\n"
        + "track - Начать отслеживание ссылки\n"
        + "untrack - Прекратить отслеживание ссылки\n"
        + "list - Показать список отслеживаемых ссылок";

    @Override
    public String getName() {
        return "/help";
    }

    @Override
    public String getDescription() {
        return "Вывести окно с командами";
    }

    @Override
    public String processCommand(Update update) {
        return HELP_COMMAND_RESPONSE;
    }
}
