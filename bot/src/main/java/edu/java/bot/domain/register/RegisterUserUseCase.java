package edu.java.bot.domain.register;

import com.pengrad.telegrambot.model.User;
import edu.java.bot.data.TrackerRepository;
import edu.java.bot.util.UserAlreadyRegisteredException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegisterUserUseCase {
    private final TrackerRepository repository;

    public RegisterUserResponse registerUser(User user) {
        try {
            repository.registerUser(user.username());
            return new RegisterUserResponse.Ok();
        } catch (UserAlreadyRegisteredException exception) {
            return new RegisterUserResponse.AlreadyRegistered();
        }
    }
}
