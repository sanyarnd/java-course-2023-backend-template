package edu.java.bot.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.service.command.Command;
import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TrackerBot extends TelegramBot {
    private final List<Command> commands;

    public TrackerBot(ApplicationConfig applicationConfig, List<Command> commands) {
        super(applicationConfig.telegramToken());
        this.commands = commands;
    }

    @PostConstruct
    public void start() {
        List<BotCommand> commandMenu = commands.stream().map(Command::toApiCommand).toList();
        execute(new SetMyCommands(commandMenu.toArray(new BotCommand[0])));
        setUpdatesListener(updates -> {
            updates.forEach(this::process);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void process(Update update) {
        for (Command command : commands) {
            if (command.supports(update)) {
                execute(command.process(update));
                return;
            }
        }
        processUnrecognizedCommand(update);
    }

    private void processUnrecognizedCommand(Update update) {
        if (update.message() != null) {
            execute(new SendMessage(
                update.message().chat().id(),
                "This command is not supported.\nUse /help to see a list of all possible commands."
            ));
        }
    }
}
