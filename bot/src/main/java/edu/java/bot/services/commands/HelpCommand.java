package edu.java.bot.services.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import edu.java.bot.bot.Bot;
import edu.java.bot.data.UsersTracks;
import edu.java.bot.services.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements ICommand {
    private static final String HELP_COMMAND_RESPONSE = "start - Введите, чтобы начать\n" +
        "help - Вывести окно с командами\n" +
        "track - Начать отслеживание ссылки\n" +
        "untrack - Прекратить отслеживание ссылки\n" +
        "list - Показать список отслеживаемых ссылок";

    @Override
    public String getName() {
        return "/help";
    }

    @Override
    public boolean processCommand(Bot bot, Update update) {
        return bot.writeToUser(update, HELP_COMMAND_RESPONSE);
    }
}
