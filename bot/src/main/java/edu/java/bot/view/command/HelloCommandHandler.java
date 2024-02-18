package edu.java.bot.view.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class HelloCommandHandler implements CommandHandler {
    @Override
    public String command() {
        return "/hello";
    }

    @Override
    public String description() {
        return "Say hello to user";
    }

    @Override
    public Optional<SendMessage> handle(Update update) {
        return Optional.of(new SendMessage(
            update.message().chat().id(),
            String.format("Hello %s", update.message().from().username())
        ));
    }
}
