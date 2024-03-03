package edu.java.bot.domain.unsubscribe;

import com.pengrad.telegrambot.model.User;
import edu.java.bot.data.LinkTrackerRepository;
import edu.java.core.exception.LinkNotTracked;
import edu.java.core.exception.UserIsNotAuthenticated;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UntrackLinkUseCase {
    private final LinkTrackerRepository repository;

    public UntrackLinkResponse untrackLink(User user, String link) {
        try {
            repository.setLinkUntracked(user.id(), link);
            return new UntrackLinkResponse.Ok();
        } catch (UserIsNotAuthenticated exception) {
            return new UntrackLinkResponse.UserIsNotDefined();
        } catch (LinkNotTracked exception) {
            return new UntrackLinkResponse.IsNotRegistered();
        }
    }
}
