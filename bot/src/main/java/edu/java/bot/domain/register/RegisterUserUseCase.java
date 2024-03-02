package edu.java.bot.domain.register;

import com.pengrad.telegrambot.model.User;
import edu.java.bot.data.LinkTrackerRepository;
import edu.java.bot.data.UserAuthRepository;
import edu.java.bot.model.UserAlreadyRegisteredException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegisterUserUseCase {
    private final UserAuthRepository repository;

    public RegisterUserResponse registerUser(User user) {
        try {
            repository.registerUser(user.id());
            return new RegisterUserResponse.Ok();
        } catch (UserAlreadyRegisteredException exception) {
            return new RegisterUserResponse.AlreadyRegistered();
        }
    }
}
