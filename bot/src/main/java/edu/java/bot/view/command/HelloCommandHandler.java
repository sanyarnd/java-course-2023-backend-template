package edu.java.bot.view.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class HelloCommandHandler implements CommandHandler {
    private final TelegramBot telegramBot;

    @Override
    public String command() {
        return "/hello";
    }

    @Override
    public String description() {
        return "Say hello to user";
    }

    @Override
    public void handle(Update update) {
        SendMessage msg = new SendMessage(update.message().chat().id(), "Hi");
        telegramBot.execute(msg);
    }
}
