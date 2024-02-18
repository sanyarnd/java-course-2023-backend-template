package edu.java.bot.domain.links;

import com.pengrad.telegrambot.model.User;
import edu.java.bot.data.TrackerRepository;
import edu.java.bot.domain.register.RegisterUserResponse;
import edu.java.bot.util.UserAlreadyRegisteredException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ViewLinksUseCase {
    private final TrackerRepository repository;

    public ViewLinksResponse viewLinks(User user) {
        try {
            var subscriptions = repository.getUserSubscriptions(user.username());
            return new ViewLinksResponse.Ok(subscriptions);
        } catch (UserAlreadyRegisteredException exception) {
            return new ViewLinksResponse.UserIsNotDefined();
        }
    }
}
