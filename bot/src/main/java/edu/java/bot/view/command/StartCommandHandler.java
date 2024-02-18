package edu.java.bot.view.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.domain.RegisterUserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class StartCommandHandler implements CommandHandler {
    private final RegisterUserUseCase register;
    private final TelegramBot telegramBot;

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "Register new user";
    }

    @Override
    public void handle(Update update) {
        new Thread(() -> {
            var response = register.registerUser(update.message().from());
            var message = switch (response) {
                case OK -> "Successfully registered!";
                case ALREADY_REGISTERED -> "You already registered!";
            };
            telegramBot.execute(new SendMessage(update.message().chat().id(), message));
        }).start();
    }
}
