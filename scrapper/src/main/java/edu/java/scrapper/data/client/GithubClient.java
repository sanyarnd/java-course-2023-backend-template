package edu.java.scrapper.data.client;

import edu.java.scrapper.model.github.GithubRepositoryResponse;

public interface GithubClient {
    GithubRepositoryResponse fetchRepository(String user, String repository);
}
