package edu.java.bot.data;

public interface UserAuthRepository {
    void registerUser(Long userId) throws UserAlreadyRegistered;

    void deleteUser(Long userId) throws UserNotRegistered;
}
