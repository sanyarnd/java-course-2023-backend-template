package edu.java.scrapper.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

public record QuestionResponse(
    @JsonProperty("items")
    List<ItemResponse> items
) {
    public record ItemResponse(
        @JsonProperty("question_id")
        long id,
        @JsonProperty("title")
        String title,
        @JsonProperty("last_edit_date")
        OffsetDateTime lastEditDate) {
    }
}
