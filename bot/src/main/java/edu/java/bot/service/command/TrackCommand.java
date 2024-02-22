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
public class TrackCommand extends Command {
    private final LinkService linkService;

    public TrackCommand(LinkService linkService) {
        this.linkService = linkService;
    }

    @Override
    public String command() {
        return "track";
    }

    @Override
    public String description() {
        return "Start tracking a link";
    }

    @Override
    public SendMessage process(Update update) {
        try {
            Link link = Link.parse(CommonUtils.cutFirstWord(update.message().text()));
            if (!linkService.isSupported(link)) {
                return new SendMessage(update.message().chat().id(),
                    "This domain is not supported yet. List of all supported domains:\n"
                        + CommonUtils.joinEnumerated(linkService.getSupportedDomains(), 1));
            }
            Optional<Link> optionalLink = linkService.find(update.message().chat().id(), link.getUrl());
            if (optionalLink.isPresent()) {
                return new SendMessage(update.message().chat().id(), "You are already tracking this link.");
            }
            linkService.addLink(update.message().chat().id(), link);
            return new SendMessage(update.message().chat().id(), "Link " + link.getUrl() + " is now being tracked.");
        } catch (URISyntaxException | MalformedURLException | IllegalArgumentException e) {
            return new SendMessage(update.message().chat().id(), "The link is not correct.");
        }
    }
}
