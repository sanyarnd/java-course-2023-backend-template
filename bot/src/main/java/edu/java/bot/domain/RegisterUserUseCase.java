package edu.java.bot.domain;

import com.pengrad.telegrambot.model.User;
import edu.java.bot.data.TrackerRepository;
import edu.java.bot.util.UserAlreadyRegisteredException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegisterUserUseCase {
    private final TrackerRepository repository;

    public enum RegisterUserResponse {
        OK,
        ALREADY_REGISTERED
    }

    public RegisterUserResponse registerUser(User user) {
        try {
            repository.registerUser(user.username());
            return RegisterUserResponse.OK;
        } catch (UserAlreadyRegisteredException exception) {
            return RegisterUserResponse.ALREADY_REGISTERED;
        }
    }
}
