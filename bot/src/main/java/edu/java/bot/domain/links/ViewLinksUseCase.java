package edu.java.bot.domain.links;

import com.pengrad.telegrambot.model.User;
import edu.java.bot.data.LinkTrackerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ViewLinksUseCase {
    private final LinkTrackerRepository repository;

    public ViewLinksResponse viewLinks(User user) {
        try {
            var subscriptions = repository.getUserTrackedLinks(user.id());
            return new ViewLinksResponse.Ok(subscriptions);
        } catch (UserNotAuthenticated exception) {
            return new ViewLinksResponse.UserIsNotDefined();
        }
    }
}
