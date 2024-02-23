package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Command;
import edu.java.bot.UserMessageListener;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "shows available commands";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), getCommandList());
    }

    private String getCommandList() {
        List<? extends Command> commands = UserMessageListener.commands();
        StringBuilder result = new StringBuilder();
        for (var command : commands) {
            result.append(command.command());
            result.append(" — ");
            result.append(command.description());
            result.append("\n");
        }
        return result.toString();
    }
}
