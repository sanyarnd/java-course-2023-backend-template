package edu.java.scrapper.data.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RepositoryOwnerDTO(
    @JsonProperty("login")
    String login,

    @JsonProperty("type")
    String type,

    @JsonProperty("id")
    int id
) {
}
