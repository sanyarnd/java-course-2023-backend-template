package edu.java.scrapper.data.network;

import edu.java.core.response.github.CommitResponse;
import edu.java.core.response.github.RepositoryResponse;
import java.util.List;

public interface GithubConnector {
    RepositoryResponse fetchRepository(String user, String repository);

    List<CommitResponse> fetchRepositoryCommits(String username, String repository);
}
