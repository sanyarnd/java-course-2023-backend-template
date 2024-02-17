package edu.java.bot.commands;

import edu.java.bot.repositories.LinksRepository;
import edu.java.bot.exceptions.UserIsNotRegisteredException;
import edu.java.bot.utils.Validation;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand extends CommandWithArgs {
    final transient LinksRepository linksRepository;
    protected TrackCommand(LinksRepository linksRepository) {
        super("/track", "Start to track link", "Enter your link");
        this.linksRepository = linksRepository;
    }

    @Override public String handleNext(long chatId, String message) throws UserIsNotRegisteredException {
        states.clearState(chatId);
        if (Validation.isLink(message)) {
            return linksRepository.addLink(chatId, message) ? "Done" : "Link has already added";
        } else {
            return "Incorrect URL";
        }
    }
}
