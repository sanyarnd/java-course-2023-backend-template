package edu.java.bot.bot.messageProcessor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.bot.commands.Command;
import edu.java.bot.bot.commands.HelpCommand;
import edu.java.bot.bot.commands.ListCommand;
import edu.java.bot.bot.commands.StartCommand;
import edu.java.bot.bot.commands.TrackCommand;
import edu.java.bot.bot.commands.UntrackCommand;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

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
        for (var cmd : availableCommands) {
            if (cmd.supports(update)) {
                return cmd.handle(update);
            }
        }
        if (update.message().text().contains("/")) {
            return new SendMessage(update.message().chat().id(), "Unsupported");
        }
        return null;

    }
}
