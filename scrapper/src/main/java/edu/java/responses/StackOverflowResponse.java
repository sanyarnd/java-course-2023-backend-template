package edu.java.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StackOverflowResponse {
    private List<Item> items;

    @Data
    public static class Item {
        private String title;

        @JsonProperty("last_activity_date")
        private OffsetDateTime lastActivityDate;
        //TODO::add more fields
    }
}
