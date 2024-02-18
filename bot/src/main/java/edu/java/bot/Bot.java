package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.processor.UserMessageProcessor;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Bot implements UpdatesListener {

    private final TelegramBot telegramBot;
    private final UserMessageProcessor processor;



    public Bot(TelegramBot telegramBot, UserMessageProcessor processor) {
        this.telegramBot = telegramBot;
        this.processor = processor;
        telegramBot.execute(createMenu());
        telegramBot.setUpdatesListener(this);
    }

    private SetMyCommands createMenu() {
        return new SetMyCommands(
            processor.commands()
                .stream()
                .map(cmd -> new BotCommand(
                    cmd.command(),
                    cmd.description()
                )).toArray(BotCommand[]::new)
        );
    }

    @Override
    public int process(List<Update> updateList) {
        for (var update : updateList) {
            SendMessage message = processor.process(update);
            telegramBot.execute(message);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
