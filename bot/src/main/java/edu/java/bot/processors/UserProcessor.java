package edu.java.bot.processors;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.HelpCommand;
import edu.java.bot.commands.ICommand;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.commands.UntrackCommand;
import edu.java.bot.repository.SyntheticRecentlyUsedRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UserProcessor {

    public List<? extends ICommand> multiLineCommands() {
        List<ICommand> commands = new ArrayList<>();
        commands.add(new TrackCommand());
        commands.add(new UntrackCommand());

        return commands;
    }

    public List<? extends ICommand> commands() {
        List<ICommand> commands = new ArrayList<>();
        commands.add(new HelpCommand(""));
        commands.add(new ListCommand());
        commands.add(new StartCommand());
        commands.add(new TrackCommand());
        commands.add(new UntrackCommand());

        commands.add(new HelpCommand(helpCommandPreprocess(commands)));
        commands.removeFirst();

        return commands;
    }

    private String helpCommandPreprocess(List<? extends ICommand> commands) {
        var sb = new StringBuilder();
        for (var c : commands) {
            sb.append(c.command()).append(" -- ").append(c.description()).append("\n");
        }
        return sb.toString();
    }

    public SendMessage process(Update update) {
        for (var command : commands()) {
            if (command.supports(update)) {
                SyntheticRecentlyUsedRepository.set(update.message().chat().id(), update.message().text());

                return command.handle(update);
            }
        }

        for (var command : multiLineCommands()) {
            if (command.command().equals(SyntheticRecentlyUsedRepository.getByChatId(update.message().chat().id()))) {
                return command.userResponseHandler(update);
            }
        }


        return new SendMessage(update.message().chat().id(), "Команда не подерживается");
    }
}
