package edu.java.bot.view.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import java.util.regex.Pattern;

@Controller
@AllArgsConstructor
public class HelloWorldCommandHandler implements CommandHandler {
    private final TelegramBot telegramBot;

    @Override
    public Pattern getPattern() {
        return Pattern.compile("/hello");
    }

    @Override
    public void handle(Update update) {
        SendMessage msg = new SendMessage(update.message().chat().id(), "Hello!");
        telegramBot.execute(msg);
    }
}
