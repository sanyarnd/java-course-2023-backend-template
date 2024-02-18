package edu.java.bot.util;

import lombok.Getter;

@Getter
public enum CommandEnum {
    START("/start", "запустить бота"),
    HELP("/help", "список команд"),
    TRACK("/track", "начать отслеживание ссылки"),
    UNTRACK("/untrack", "прекратить отслеживание ссылки"),
    LIST("/list", "показать список отслеживаемых ссылок"),
    CANCEL("/cancel", "отменить действие");

    private final String command;
    private final String description;

    CommandEnum(String command, String description) {
        this.command = command;
        this.description = description;
    }
}
