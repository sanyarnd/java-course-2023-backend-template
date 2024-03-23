package edu.java.bot.view.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.domain.RegisterUserUseCase;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class StartCommandHandler implements CommandHandler {
    private final RegisterUserUseCase register;

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "Register new user";
    }

    @Override
    public Optional<SendMessage> handle(Update update) {
        var response = register.registerUser(update.message().from());
        return Optional.of(new SendMessage(update.message().chat().id(), response.getMessage()));
    }
}
