package edu.java.bot.domain;

import com.pengrad.telegrambot.model.User;
import edu.java.bot.data.LinkTrackerRepository;
import edu.java.bot.domain.ErrorTelegramResponse;
import edu.java.bot.domain.TelegramResponse;
import edu.java.core.exception.ApiErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UntrackLinkUseCase {
    private final LinkTrackerRepository repository;

    public TelegramResponse untrackLink(User user, String link) {
        try {
            repository.setLinkUntracked(user.id(), link);
            return new TelegramResponse("Link unsubscribed");
        } catch (ApiErrorException exception) {
            return new ErrorTelegramResponse(exception);
        }
    }
}
