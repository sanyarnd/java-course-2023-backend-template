package edu.java.core.response.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Author(
        @JsonProperty("name")
        String name,

        @JsonProperty("email")
        String email,

        @JsonProperty("date")
        String date
) {
}