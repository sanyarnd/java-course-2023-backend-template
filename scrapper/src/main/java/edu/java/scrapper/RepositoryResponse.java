package edu.java.scrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record RepositoryResponse(
    @JsonProperty("id")
    long id,
    @JsonProperty("name")
    String repositoryName,
    @JsonProperty("pushed_at")
    OffsetDateTime pushedAt
    ) {
}
