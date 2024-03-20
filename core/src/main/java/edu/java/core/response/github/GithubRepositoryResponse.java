package edu.java.core.response.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GithubRepositoryResponse(
    @JsonProperty("name")
    String name,

    @JsonProperty("full_name")
    String fullName,

    @JsonProperty("description")
    String description,

    @JsonProperty("subscribers_count")
    int subscribersCount,

    @JsonProperty("open_issues_count")
    int openIssuesCount,

    @JsonProperty("watchers_count")
    int watchersCount,

    @JsonProperty("forks_count")
    int forksCount
) {
}
