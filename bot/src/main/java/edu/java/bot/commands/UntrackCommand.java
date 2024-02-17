package edu.java.bot.commands;

import edu.java.bot.exceptions.UserIsNotRegisteredException;
import edu.java.bot.repositories.LinksRepository;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand extends CommandWithArgs {
    final transient LinksRepository linksRepository;

    protected UntrackCommand(LinksRepository linksRepository) {
        super(
            "/untrack",
            "Stop to track link",
            "Enter the link. /list to get a list of them."
        );
        this.linksRepository = linksRepository;
    }

    @Override public String handleNext(long chatId, String message) throws UserIsNotRegisteredException {
        states.clearState(chatId);
        return linksRepository.removeLink(chatId, message) ? "Done" : "Link hasn't added";
    }
}
