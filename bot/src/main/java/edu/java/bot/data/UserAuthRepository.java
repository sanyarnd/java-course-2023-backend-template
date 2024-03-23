package edu.java.bot.data;

import edu.java.core.exception.ApiErrorException;

public interface UserAuthRepository {
    void registerUser(Long userId) throws ApiErrorException;

    void deleteUser(Long userId) throws ApiErrorException;
}
