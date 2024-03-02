package edu.java.scrapper.data.client;

import edu.java.scrapper.data.dto.github.RepositoryDTO;

public interface GithubClient {
    RepositoryDTO fetchRepository(String user, String repository);
}
