package edu.java.core.response.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Commit(
        @JsonProperty("author")
        Author author,

        @JsonProperty("message")
        String message
) {
}