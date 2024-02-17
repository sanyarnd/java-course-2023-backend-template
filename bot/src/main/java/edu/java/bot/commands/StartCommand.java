package edu.java.bot.commands;

import edu.java.bot.repositories.LinksRepository;
import org.springframework.stereotype.Component;

@Component
public class StartCommand extends CommandWithoutArgs {

    final transient LinksRepository linksRepository;

    protected StartCommand(LinksRepository linksRepository) {
        super(
            "/start",
            "Register this user",
            "You are successfully registered!"
        );
        this.linksRepository = linksRepository;
    }

    @Override public String run(long chatId) {
        states.clearState(chatId);
        return linksRepository.register(chatId) ? answer : "You have already registered!";
    }
}
