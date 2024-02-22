package edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.domain.Link;
import edu.java.bot.service.LinkService;
import edu.java.bot.util.CommonUtils;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand extends Command {
    private final LinkService linkService;

    public UntrackCommand(LinkService linkService) {
        this.linkService = linkService;
    }

    @Override
    public String command() {
        return "untrack";
    }

    @Override
    public String description() {
        return "Stop tracking a link";
    }

    @Override
    public SendMessage process(Update update) {
        try {
            Link link = Link.parse(CommonUtils.cutFirstWord(update.message().text()));
            Optional<Link> optionalLink = linkService.find(update.message().chat().id(), link.getUrl());
            if (optionalLink.isEmpty()) {
                return new SendMessage(update.message().chat().id(), "You are not tracking this link yet.");
            }
            linkService.deleteLink(update.message().chat().id(), link);
            return new SendMessage(
                update.message().chat().id(),
                "Link " + link.getUrl() + " is no longer being tracked."
            );
        } catch (URISyntaxException | MalformedURLException | IllegalArgumentException e) {
            return new SendMessage(update.message().chat().id(), "The link is not correct.");
        }
    }
}
