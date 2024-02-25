package edu.java.clients.stackoverflow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.java.clients.ClientEntityResponse;
import java.time.OffsetDateTime;

public interface StackOverflowClient {

    StackOverflowQuestionResponse getQuestions(Long stackOverflowQuestionId);

    @JsonIgnoreProperties(ignoreUnknown = true)
    record StackOverflowQuestionResponse(
        @JsonProperty("last_activity_date") OffsetDateTime updatedAt
    ) implements ClientEntityResponse {
        @Override
        public OffsetDateTime getUpdatedAt() {
            return updatedAt;
        }
    }
}
