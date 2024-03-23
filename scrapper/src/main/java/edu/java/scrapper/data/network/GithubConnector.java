package edu.java.scrapper.data.network;

import edu.java.core.response.github.GithubRepositoryResponse;

public interface GithubConnector {
    GithubRepositoryResponse fetchRepository(String user, String repository);
}
