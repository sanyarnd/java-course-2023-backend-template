package edu.java.bot.comand;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HelpCommand implements Command {

    private final List<Command> commandList;

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "displays a list of available commands";
    }

    @Override
    public SendMessage handle(Update update) {
        StringBuilder helpMessage = new StringBuilder();
        commandList.forEach(cm -> helpMessage.append(cm.command()).append(" - ").append(cm.description()).append("\n"));
        return new SendMessage(update.message().chat().id(), helpMessage.toString());
    }
}
