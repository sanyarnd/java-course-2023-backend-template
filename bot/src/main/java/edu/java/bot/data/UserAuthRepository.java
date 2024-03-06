package edu.java.bot.data;

import edu.java.core.exception.UserAlreadyRegistered;
import edu.java.core.exception.UserNotRegistered;

public interface UserAuthRepository {
    void registerUser(Long userId) throws UserAlreadyRegistered;

    void deleteUser(Long userId) throws UserNotRegistered;
}
