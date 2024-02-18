package edu.java.bot.domain.unsubscribe;

public sealed interface UntrackLinkResponse {
    non-sealed class Ok implements UntrackLinkResponse {
    }

    non-sealed class IsNotRegistered implements UntrackLinkResponse {
    }

    non-sealed class UserIsNotDefined implements UntrackLinkResponse {
    }
}
