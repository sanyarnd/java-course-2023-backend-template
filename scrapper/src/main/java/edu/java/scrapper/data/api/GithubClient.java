package edu.java.scrapper.data.api;

import edu.java.scrapper.data.dto.github.RepositoryDTO;

public interface GithubClient {
    RepositoryDTO fetchRepository(String user, String repository);
}
