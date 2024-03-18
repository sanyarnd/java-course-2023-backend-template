package edu.java.scrapper.data.network;

import edu.java.core.response.github.GithubRepositoryResponse;

public interface GithubClient extends ScrapperClient {
    GithubRepositoryResponse fetchRepository(String user, String repository);
}
