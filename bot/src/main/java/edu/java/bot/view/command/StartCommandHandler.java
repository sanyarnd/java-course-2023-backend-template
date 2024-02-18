package edu.java.bot.view.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class StartCommandHandler implements CommandHandler {
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

    }
}
