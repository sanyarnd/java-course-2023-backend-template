package edu.java.bot.view.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class HelpCommandHandler implements CommandHandler {
    private List<CommandHandler> handlers;

    @Autowired
    public void setHandlers(List<CommandHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "Describes all commands available";
    }

    @Override
    public Optional<SendMessage> handle(Update update) {
        var message = handlers.stream()
            .map(commandHandler -> String.format("%s\n%s", commandHandler.command(), commandHandler.description()))
            .toList();
        return Optional.of(new SendMessage(update.message().chat().id(), String.join("\n", message)));
    }
}
