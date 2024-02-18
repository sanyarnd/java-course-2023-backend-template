package edu.java.bot.bot.messageProcessor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.bot.commands.Command;
import edu.java.bot.bot.commands.HelpCommand;
import edu.java.bot.bot.commands.ListCommand;
import edu.java.bot.bot.commands.StartCommand;
import edu.java.bot.bot.commands.TrackCommand;
import edu.java.bot.bot.commands.UntrackCommand;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserMessageProcessorImpl implements UserMessageProcessor {
    // NOTE::// поменять на HashMap и изменить process
    private final ArrayList<Command> availableCommands = new ArrayList<>();

    public UserMessageProcessorImpl() {
        availableCommands.add(new HelpCommand());
        availableCommands.add(new ListCommand());
        availableCommands.add(new StartCommand());
        availableCommands.add(new TrackCommand());
        availableCommands.add(new UntrackCommand());
    }

    @Override
    public List<? extends Command> commands() {
        return availableCommands;
    }

    @Override
    public SendMessage process(Update update) {
        switch (update.message().text().split(" ")[0]) {
            case "/help":
                return availableCommands.get(0).handle(update);
            case "/list":
                return availableCommands.get(1).handle(update);
            case "/start":
                return availableCommands.get(2).handle(update);
            case "/track":
                return availableCommands.get(3).handle(update);
            case "/untrack":
                return availableCommands.get(4).handle(update);

        }
        return null;
    }
}
