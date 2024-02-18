package edu.java.bot.domain.subscribe;

public sealed interface TrackLinkResponse {
    non-sealed class Ok implements TrackLinkResponse {
    }

    non-sealed class AlreadyRegistered implements TrackLinkResponse {
    }

    non-sealed class UserIsNotDefined implements TrackLinkResponse {
    }
}
