package edu.java.bot.view.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.domain.subscribe.TrackLinkResponse;
import edu.java.bot.domain.subscribe.TrackLinkUseCase;
import java.util.Optional;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class TrackCommandHandler implements CommandHandler {
    private final TrackLinkUseCase tracker;

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "Set link to tracked";
    }

    @Override
    public Pattern getPattern() {
        return Pattern.compile("/track (\\S+)");
    }

    @Override
    public Optional<SendMessage> handle(Update update) {
        var matcher = getPattern().matcher(update.message().text());
        if (matcher.find()) {
            var response = tracker.trackLink(update.message().from(), matcher.group(1));
            String message = null;
            if (response instanceof TrackLinkResponse.Ok) {
                message = "Link subscribed";
            } else if (response instanceof TrackLinkResponse.AlreadyRegistered) {
                message = "Link is already registered!";
            } else if (response instanceof TrackLinkResponse.UserIsNotDefined) {
                message = "You have to login first!";
            }
            return Optional.of(new SendMessage(update.message().chat().id(), message));
        } else {
            return Optional.empty();
        }
    }
}
