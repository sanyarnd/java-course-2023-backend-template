package edu.java.bot.domain.subscribe;

import com.pengrad.telegrambot.model.User;
import edu.java.bot.data.LinkTrackerRepository;
import edu.java.core.exception.LinkAlreadyTracked;
import edu.java.core.exception.UserNotAuthenticated;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TrackLinkUseCase {
    private final LinkTrackerRepository repository;

    public TrackLinkResponse trackLink(User user, String link) {
        try {
            repository.setLinkTracked(user.id(), link);
            return new TrackLinkResponse.Ok();
        } catch (UserNotAuthenticated exception) {
            return new TrackLinkResponse.UserIsNotDefined();
        } catch (LinkAlreadyTracked exception) {
            return new TrackLinkResponse.AlreadyRegistered();
        }
    }
}
