package edu.java.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record StackoverflowQuestion(@JsonProperty("last_activity_date") OffsetDateTime lastActivityDate) {
}
