package edu.java.scrapper.model.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RepositoryOwnerResponse(
    @JsonProperty("login")
    String login,

    @JsonProperty("type")
    String type,

    @JsonProperty("id")
    int id
) {
}
