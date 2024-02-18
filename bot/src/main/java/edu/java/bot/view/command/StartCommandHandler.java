package edu.java.bot.view.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.domain.register.RegisterUserResponse;
import edu.java.bot.domain.register.RegisterUserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import java.util.Optional;

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
        String message = null;
        if (response instanceof RegisterUserResponse.Ok) {
            message = "Registration successful";
        } else if (response instanceof RegisterUserResponse.AlreadyRegistered) {
            message = "You already logged in!";
        }
        return Optional.of(new SendMessage(update.message().chat().id(), message));
    }
}
