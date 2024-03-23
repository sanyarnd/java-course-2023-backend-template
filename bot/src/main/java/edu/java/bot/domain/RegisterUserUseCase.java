package edu.java.bot.domain;

import com.pengrad.telegrambot.model.User;
import edu.java.bot.data.UserAuthRepository;
import edu.java.core.exception.ApiErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegisterUserUseCase {
    private final UserAuthRepository repository;

    public TelegramResponse registerUser(User user) {
        try {
            repository.registerUser(user.id());
            return new TelegramResponse("Registration successful");
        } catch (ApiErrorException exception) {
            return new ErrorTelegramResponse(exception);
        }
    }
}
