package edu.java.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubResponse {
    private String name;
    private String description;
    @JsonProperty("pushed_at")
    private OffsetDateTime pushedAt;
    //TODO::add fields
}
