package edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.domain.Link;
import edu.java.bot.service.LinkService;
import edu.java.bot.util.CommonUtils;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ListCommand extends Command {
    private final LinkService linkService;

    public ListCommand(LinkService linkService) {
        this.linkService = linkService;
    }

    @Override
    public String command() {
        return "list";
    }

    @Override
    public String description() {
        return "Show all tracked links";
    }

    @Override
    public SendMessage process(Update update) {
        List<Link> links = linkService.findAll(update.message().chat().id());
        if (links.isEmpty()) {
            return new SendMessage(update.message().chat().id(),
                "You don't have any tracked links.\nUse /track to start tracking.");
        }
        return new SendMessage(update.message().chat().id(),
            "Your tracked links:\n" + CommonUtils.joinEnumerated(links, 1));
    }
}
