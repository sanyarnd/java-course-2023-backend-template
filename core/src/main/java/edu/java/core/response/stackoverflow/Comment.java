package edu.java.core.response.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Comment(

        @JsonProperty("owner")
        Owner owner,

        @JsonProperty("score")
        Integer score,

        @JsonProperty("edited")
        Boolean edited
) {
}
