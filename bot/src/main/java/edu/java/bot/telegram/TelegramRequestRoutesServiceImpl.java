package edu.java.bot.telegram;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.model.Link;
import edu.java.bot.model.TelegramAnswer;
import edu.java.bot.model.UserMessage;
import edu.java.bot.repositories.UserLinksRepository;
import edu.java.bot.utils.LinkUtils;
import java.net.URI;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TelegramRequestRoutesServiceImpl implements TelegramRequestRoutesService {

    private final UserLinksRepository userLinksRepository;

    public TelegramRequestRoutesServiceImpl(UserLinksRepository userLinksRepository) {
        this.userLinksRepository = userLinksRepository;
    }

    @Override
    public TelegramAnswer start(UserMessage message) {
        log.info("User registered {}", message.chatId());
        return new TelegramAnswer(Optional.of("User registered"));
    }

    @Override
    public TelegramAnswer help(UserMessage message) {
        log.info("Help for user {}", message.chatId());
        return new TelegramAnswer(Optional.of(
            """
                Bot for notify about updates onto your favorite websites.
                Commands:
                /start -- user registration
                /help -- help message
                /track {link} -- start of tracking link for you
                /untrack {link} -- remove link from your tracking list
                /list -- list of your tracking links
                """
        ));
    }

    @Override
    public TelegramAnswer track(UserMessage message) {
        var startLength = "/track ".length();
        if (message.text().length() <= startLength) {
            return new TelegramAnswer(Optional.of("Please, send a link"));
        }
        Link link;
        try {
            link = LinkUtils.convertUriToLink(URI.create(message.text().substring(startLength)));
        } catch (IllegalArgumentException e) {
            return new TelegramAnswer(Optional.of("Sended link is incorrect " + e.getMessage()));
        }
        if (link.host() == null) {
            return new TelegramAnswer(Optional.of("Sended link host is incorrect"));
        }
        log.info("User {} started tracking link {}", message.chatId(), link);
        var success = userLinksRepository.addUserLinks(message.chatId(), link);
        var resMessage = success ? "Link tracking started" : "Link already tracking";
        return new TelegramAnswer(Optional.of(resMessage));
    }

    @Override
    public TelegramAnswer untrack(UserMessage message) {
        var startLength = "/untrack ".length();
        if (message.text().length() <= startLength) {
            return new TelegramAnswer(Optional.of("Please, send a link"));
        }
        Link link;
        try {
            link = LinkUtils.convertUriToLink(URI.create(message.text().substring(startLength)));
        } catch (IllegalArgumentException e) {
            return new TelegramAnswer(Optional.of("Sended link is incorrect " + e.getMessage()));
        }
        log.info("User {} ended tracking link {}", message.chatId(), link);
        var success = userLinksRepository.removeUserLinks(message.chatId(), link);
        var resMessage = success ? "Link tracking stopped" : "Link isn't tracking";
        return new TelegramAnswer(Optional.of(resMessage));
    }

    @Override
    public TelegramAnswer list(UserMessage message) {
        log.info("User {} requested link list", message.chatId());
        var links = userLinksRepository.getLinksByUser(message.chatId());
        String resMessage;
        if (links.isEmpty()) {
            resMessage = "No tracking links.";
        } else {
            resMessage = links.stream().map(Link::toString)
                .collect(Collectors.joining("\n- ", "Tracking links:\n- ", ""));
        }
        return new TelegramAnswer(Optional.of(resMessage));
    }
}
