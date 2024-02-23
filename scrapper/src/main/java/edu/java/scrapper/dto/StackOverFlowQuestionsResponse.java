package edu.java.scrapper.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record StackOverFlowQuestionsResponse(
    @JsonProperty("items") List<StackOverFlowDetailsResponse> questions
) {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record StackOverFlowDetailsResponse(
        @JsonProperty("last_edit_date") OffsetDateTime updatedAt,
        @JsonProperty("question_id") long id
    ) {
    }
}
