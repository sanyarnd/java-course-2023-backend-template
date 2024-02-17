package edu.java.bot.commands;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HelpCommand extends CommandWithoutArgs {

    final List<AbstractCommand> commands;

    protected HelpCommand(List<AbstractCommand> commands) {
        super(
            "/help",
            "Help",
            commands.stream().map(x -> String.format("%s - %s", x.command(), x.description()))
                .collect(Collectors.joining("\n"))
        );
        this.commands = commands;
    }
}
