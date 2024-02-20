package edu.java.bot.domain.register;

public sealed interface RegisterUserResponse {
    non-sealed class Ok implements RegisterUserResponse {
    }

    non-sealed class AlreadyRegistered implements RegisterUserResponse {
    }
}
