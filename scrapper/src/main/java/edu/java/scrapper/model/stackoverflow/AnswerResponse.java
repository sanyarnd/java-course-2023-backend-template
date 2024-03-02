package edu.java.scrapper.model.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AnswerResponse(
    @JsonProperty("owner")
    AnswerOwnerResponse owner,

    @JsonProperty("score")
    int score,

    @JsonProperty("is_accepted")
    boolean isAccepted,

    @JsonProperty("last_activity_date")
    int lastActivityDate,

    @JsonProperty("creation_date")
    int creationDate,

    @JsonProperty("answer_id")
    int answerId,

    @JsonProperty("question_id")
    int questionId,

    @JsonProperty("last_edit_date")
    int lastEditDate
) {
}
