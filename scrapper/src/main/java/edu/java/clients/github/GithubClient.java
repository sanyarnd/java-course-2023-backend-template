package edu.java.clients.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.java.clients.ClientEntityResponse;
import java.time.OffsetDateTime;

public interface GithubClient {

    GithubRepositoryResponse getRepository(GithubRepository githubRepository);

    record GithubRepository(String owner, String repo) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    record GithubRepositoryResponse(
        @JsonProperty("updated_at") OffsetDateTime updatedAt
    ) implements ClientEntityResponse {
        @Override
        public OffsetDateTime getUpdatedAt() {
            return updatedAt;
        }
    }
}
