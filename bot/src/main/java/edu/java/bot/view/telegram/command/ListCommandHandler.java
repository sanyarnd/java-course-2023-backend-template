package edu.java.bot.view.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.domain.ViewLinksUseCase;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class ListCommandHandler implements CommandHandler {
    private final ViewLinksUseCase viewer;

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "View tracked links";
    }

    @Override
    public Optional<SendMessage> handle(Update update) {
        var response = viewer.viewLinks(update.message().from());
        return Optional.of(new SendMessage(update.message().chat().id(), response.getMessage()));
    }
}
