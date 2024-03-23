package edu.java.bot.domain;

import com.pengrad.telegrambot.model.User;
import edu.java.bot.data.LinkTrackerRepository;
import edu.java.bot.domain.model.ErrorTelegramResponse;
import edu.java.bot.domain.model.TelegramResponse;
import edu.java.core.exception.ApiErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TrackLinkUseCase {
    private final LinkTrackerRepository repository;

    public TelegramResponse trackLink(User user, String link) {
        try {
            repository.setLinkTracked(user.id(), link);
            return new TelegramResponse("Link subscribed");
        } catch (ApiErrorException exception) {
            return new ErrorTelegramResponse(exception);
        }
    }
}
