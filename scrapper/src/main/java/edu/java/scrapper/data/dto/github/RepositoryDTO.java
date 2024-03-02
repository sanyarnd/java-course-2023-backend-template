package edu.java.scrapper.data.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record RepositoryDTO(
    @JsonProperty("pushed_at")
    OffsetDateTime pushedAt,

    @JsonProperty("subscribers_count")
    int subscribersCount,

    @JsonProperty("id")
    int id,

    @JsonProperty("forks")
    int forks,

    @JsonProperty("visibility")
    String visibility,

    @JsonProperty("network_count")
    int networkCount,

    @JsonProperty("full_name")
    String fullName,

    @JsonProperty("size")
    int size,

    @JsonProperty("name")
    String name,

    @JsonProperty("open_issues_count")
    int openIssuesCount,

    @JsonProperty("description")
    String description,

    @JsonProperty("created_at")
    OffsetDateTime createdAt,

    @JsonProperty("updated_at")
    OffsetDateTime updatedAt,

    @JsonProperty("owner")
    RepositoryOwnerDTO owner,

    @JsonProperty("open_issues")
    int openIssues,

    @JsonProperty("watchers_count")
    int watchersCount,

    @JsonProperty("forks_count")
    int forksCount
) {
}
