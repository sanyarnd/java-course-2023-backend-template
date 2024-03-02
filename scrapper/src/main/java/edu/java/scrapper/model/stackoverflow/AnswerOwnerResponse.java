package edu.java.scrapper.model.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AnswerOwnerResponse(
    @JsonProperty("account_id")
    int accountId,

    @JsonProperty("user_type")
    String userType,

    @JsonProperty("user_id")
    int userId,

    @JsonProperty("reputation")
    int reputation,

    @JsonProperty("display_name")
    String displayName
) {
}
