package edu.java.core.response.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubRepositoryCommitResponse(
        @JsonProperty("author")
        Author author,

        @JsonProperty("commit")
        Commit commit
) {
}