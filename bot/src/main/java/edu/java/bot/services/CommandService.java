package edu.java.bot.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter @Component
public class CommandService {
    private static final String UNDEFIENED_COMMAND_RESPONSE = "Извините, но данная" +
        " комманда не поддерживается, для вывода списка доступных комманд" +
        " введите /help";

    private static final String HELP_COMMAND_RESPONSE = "start - Введите, чтобы начать\n" +
        "help - Вывести окно с командами\n" +
        "track - Начать отслеживание ссылки\n" +
        "untrack - Прекратить отслеживание ссылки\n" +
        "list - Показать список отслеживаемых ссылок";
    private TelegramBot bot;

    public CommandService() {
    }
    public boolean processCommand(Update update) {

        String chatId = update.message().chat().id().toString();
        String text = update.message().text();
        System.out.println(text);
        switch (text) {
            case "/help":
                return processHelpCommand(chatId);
            default:
                return processUndefinedCommand(chatId);
        }

    }

    private boolean processUndefinedCommand(String chatId) {
        SendMessage request = new SendMessage(chatId, UNDEFIENED_COMMAND_RESPONSE);
        SendResponse response = bot.execute(request);
        return response.isOk();
    }

    private boolean processHelpCommand(String chatId) {
        SendMessage request = new SendMessage(chatId, HELP_COMMAND_RESPONSE);
        SendResponse response = bot.execute(request);
        return response.isOk();
    }
}
