package edu.java.bot.commands;

import com.pengrad.telegrambot.model.BotCommand;
import edu.java.bot.repositories.ChatStateRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractCommand extends BotCommand implements Command {
    protected final String answer;
    @Getter protected final boolean awaitingArgs;
    @Autowired protected transient ChatStateRepository states;

    protected AbstractCommand(String command, String description, String answer, boolean awaitingArgs) {
        super(command, description);
        this.answer = answer;
        this.awaitingArgs = awaitingArgs;
    }
}
