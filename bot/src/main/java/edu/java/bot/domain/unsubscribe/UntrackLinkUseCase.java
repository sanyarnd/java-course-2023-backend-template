package edu.java.bot.domain.unsubscribe;

import com.pengrad.telegrambot.model.User;
import edu.java.bot.data.TrackerRepository;
import edu.java.bot.model.LinkIsNotTrackedException;
import edu.java.bot.model.UserIsNotRegisteredException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UntrackLinkUseCase {
    private final TrackerRepository repository;

    public UntrackLinkResponse untrackLink(User user, String link) {
        try {
            repository.unsubscribeToLinkUpdates(user.username(), link);
            return new UntrackLinkResponse.Ok();
        } catch (UserIsNotRegisteredException exception) {
            return new UntrackLinkResponse.UserIsNotDefined();
        } catch (LinkIsNotTrackedException exception) {
            return new UntrackLinkResponse.IsNotRegistered();
        }
    }
}
