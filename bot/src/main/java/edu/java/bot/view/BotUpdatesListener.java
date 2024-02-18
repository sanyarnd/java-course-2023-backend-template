package edu.java.bot.view;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.view.command.CommandHandler;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BotUpdatesListener implements UpdatesListener {
    private final TelegramBot telegramBot;
    private final BotExceptionHandler exceptionHandler;
    private final List<CommandHandler> commandHandlers;

    @PostConstruct
    private void subscribeToUpdates() {
        telegramBot.setUpdatesListener(this, exceptionHandler);
    }

    @Override
    public int process(List<Update> list) {
        for (Update update : list) {
            var handler = commandHandlers.stream()
                .filter(commandHandler -> {
                    var matcher = commandHandler.getPattern().matcher(update.message().text());
                    return matcher.matches();
                })
                .findAny();
            handler.ifPresentOrElse(commandHandler -> commandHandler.handle(update), () -> {
                SendMessage msg = new SendMessage(update.message().chat().id(), "Unknown command!");
                telegramBot.execute(msg);
            });
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
