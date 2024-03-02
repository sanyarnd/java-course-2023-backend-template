package edu.java.bot.domain.subscribe;

import com.pengrad.telegrambot.model.User;
import edu.java.bot.data.TrackerRepository;
import edu.java.bot.model.LinkAlreadyTrackedException;
import edu.java.bot.model.UserIsNotRegisteredException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TrackLinkUseCase {
    private final TrackerRepository repository;

    public TrackLinkResponse trackLink(User user, String link) {
        try {
            repository.subscribeToLinkUpdates(user.username(), link);
            return new TrackLinkResponse.Ok();
        } catch (UserIsNotRegisteredException exception) {
            return new TrackLinkResponse.UserIsNotDefined();
        } catch (LinkAlreadyTrackedException exception) {
            return new TrackLinkResponse.AlreadyRegistered();
        }
    }
}
