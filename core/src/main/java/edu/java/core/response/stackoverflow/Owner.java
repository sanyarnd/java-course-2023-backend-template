package edu.java.core.response.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Owner(
        @JsonProperty("display_name")
        String displayName,

        @JsonProperty("user_type")
        String userType,

        @JsonProperty("reputation")
        Integer reputation
) {
}