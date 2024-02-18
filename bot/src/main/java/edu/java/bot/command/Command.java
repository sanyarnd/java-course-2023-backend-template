package edu.java.bot.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Command {
    START("/start", "Запустить бота ▶"),
    HELP("/help", "Справка по командам ❓"),
    TRACK("/track", "Начать отслеживание обновлений ссылки 📌"),
    UNTRACK("/untrack", "Прекратить отслеживание обновлений ссылки \uD83D\uDED1"),
    LIST("/list", "Просмотреть список отслеживаемых ссылок 📋");


    private final String commandName;
    private final String commandDescription;
}
