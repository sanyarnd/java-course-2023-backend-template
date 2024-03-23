package edu.java.core.response.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CommitResponse(
        @JsonProperty("author")
        Author author,

        @JsonProperty("commit")
        Commit commit
) {
}
