package edu.java.bot.domain;

import com.pengrad.telegrambot.model.User;
import edu.java.bot.data.LinkTrackerRepository;
import edu.java.core.exception.ApiErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ViewLinksUseCase {
    private final LinkTrackerRepository repository;

    public TelegramResponse viewLinks(User user) {
        try {
            return new TelegramResponse(String.join("\n", repository.getUserTrackedLinks(user.id())));
        } catch (ApiErrorException exception) {
            return new ErrorTelegramResponse(exception);
        }
    }
}
