package edu.java.core.response.github;


import edu.java.core.util.JsonSerializer;
import java.util.List;

public interface GithubJsonSerializer extends JsonSerializer<GithubPersistenceData> {
    GithubPersistenceData combine(GithubRepositoryResponse response, List<GithubRepositoryCommitResponse> commitResponse);
}
