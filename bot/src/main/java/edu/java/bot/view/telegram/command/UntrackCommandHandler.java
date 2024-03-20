package edu.java.bot.view.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.domain.UntrackLinkUseCase;
import java.util.Optional;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class UntrackCommandHandler implements CommandHandler {
    private final UntrackLinkUseCase untracker;

    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "Set link to untracked";
    }

    @Override
    public Pattern getPattern() {
        return Pattern.compile("/untrack (\\S+)");
    }

    @Override
    public Optional<SendMessage> handle(Update update) {
        var matcher = getPattern().matcher(update.message().text());
        if (matcher.find()) {
            var response = untracker.untrackLink(update.message().from(), matcher.group(1));
            return Optional.of(new SendMessage(update.message().chat().id(), response.getMessage()));
        } else {
            return Optional.empty();
        }
    }
}
