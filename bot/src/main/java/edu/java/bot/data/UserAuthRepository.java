package edu.java.bot.data;

public interface UserAuthRepository {
    void registerUser(Long userId);

    void deleteUser(Long userId);
}
