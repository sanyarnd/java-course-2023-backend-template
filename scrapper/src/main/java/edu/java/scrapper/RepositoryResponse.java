package edu.java.scrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record RepositoryResponse(
    long id,

    @JsonProperty("name")
    String repositoryName,

    @JsonProperty("login")
    String owner,

    @JsonProperty("updated_at")
    OffsetDateTime updatedAt,

    @JsonProperty("pushed_at")
    OffsetDateTime pushedAt) {

}
