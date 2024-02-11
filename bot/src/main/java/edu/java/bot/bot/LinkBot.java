package edu.java.bot.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.command.Command;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.service.UserMessageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class LinkBot extends TelegramBot {
    private final UserMessageProcessor userMessageProcessor;

    @Autowired
    public LinkBot(ApplicationConfig config, List<Command> commands, UserMessageProcessor userMessageProcessor) {
        super(config.telegramToken());
        this.userMessageProcessor = userMessageProcessor;

        List<BotCommand> botApiCommands = commands.stream().map(Command::toApiCommand).toList();
        setMyCommands(botApiCommands);
        setUpdatesListener(this::processUpdates);
    }

    public int processUpdates(List<Update> updates) {
        for (var update: updates) {
            execute(userMessageProcessor.processUpdate(update));
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void setMyCommands(List<BotCommand> botApiCommands) {
        execute(new SetMyCommands(botApiCommands.toArray(BotCommand[]::new)));
    }
}
