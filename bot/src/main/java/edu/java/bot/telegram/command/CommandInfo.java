package edu.java.bot.telegram.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommandInfo {
    START("/start", "Зарегистрировать пользователя."),
    HELP("/help", "Вывести окно с командами."),
    TRACK("/track", "Начать отслеживание ссылки."),
    UNTRACK("/untrack", "Прекратить отслеживание ссылки."),
    LIST("/list", "Показать список отслеживаемых ссылок.");

    private final String type;
    private final String description;
}
