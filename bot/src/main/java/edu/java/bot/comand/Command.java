package edu.java.bot.comand;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public interface Command {

    String command();

    String description();

    SendMessage handle(Update update);

    default boolean supports(Update update) {
        if (update.message() != null && update.message().text() != null) {
            int spaceIndex = update.message().text().indexOf(" ");
            String command = update.message().text();
            if (spaceIndex != -1) {
                command = update.message().text().substring(0, spaceIndex);
            }
            return command().equalsIgnoreCase(command);
        }
        return false;
    }

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }
}
