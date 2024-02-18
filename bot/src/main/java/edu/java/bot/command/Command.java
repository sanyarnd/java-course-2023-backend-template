package edu.java.bot.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Command {
    START("/start", "Запустить бота ▶️"),
    HELP("/help", "Справка по командам ℹ️"),
    TRACK("/track", "Начать отслеживание обновлений ссылки 🔔"),
    UNTRACK("/untrack", "Прекратить отслеживание обновлений ссылки ⛔"),
    LIST("/list", "Просмотреть список отслеживаемых ссылок 📋");

    private final String commandName;
    private final String commandDescription;
}
