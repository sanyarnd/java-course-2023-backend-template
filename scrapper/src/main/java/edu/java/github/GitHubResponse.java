package edu.java.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubResponse(
    long id,
    String name,
    @JsonProperty("updated_at") String updatedAt,
    @JsonProperty("pushed_at") String pushedAt
) {
}
