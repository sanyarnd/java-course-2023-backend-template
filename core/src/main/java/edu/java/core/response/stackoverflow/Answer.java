package edu.java.core.response.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Answer(
        @JsonProperty("owner")
        Owner owner,

        @JsonProperty("score")
        int score
) {
}
