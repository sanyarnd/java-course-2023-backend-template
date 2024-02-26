package edu.java.bot.bot.messageProcessor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.bot.commands.Command;
import edu.java.bot.bot.commands.CommandConfiguration;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class UserMessageProcessorImpl implements UserMessageProcessor {
    private final ArrayList<Command> availableCommands = new ArrayList<>();

    public UserMessageProcessorImpl() {
        var ctx = new AnnotationConfigApplicationContext(CommandConfiguration.class);
        var beans = ctx.getBeansOfType(Command.class);

        availableCommands.addAll(beans.values());
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

        if (update.message().text().startsWith("/")) {
            return new SendMessage(update.message().chat().id(), "Unsupported");
        }

        return null;
    }
}
