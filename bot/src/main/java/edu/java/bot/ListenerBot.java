package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.command.Command;
import edu.java.bot.message.MessageProcessor;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListenerBot implements Bot {
    private final TelegramBot listenerBot;
    private final MessageProcessor messageProcessor;

    public ListenerBot(TelegramBot listenerBot, MessageProcessor messageProcessor) {
        this.listenerBot = listenerBot;
        this.messageProcessor = messageProcessor;
    }

    public void createMenu() {
        List<? extends Command> commands = messageProcessor.commands();
        BotCommand[] menu = new BotCommand[commands.size()];
        for (int i = 0; i < commands.size(); i++) {
            menu[i] = commands.get(i).toApiCommand();
        }
        listenerBot.execute(new SetMyCommands(menu));
    }

    @Override
    public void start() {
        createMenu();
        listenerBot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                listenerBot.execute(process(update));
            }

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    @Override
    public SendMessage process(Update update) {
        return messageProcessor.process(update);
    }
}
