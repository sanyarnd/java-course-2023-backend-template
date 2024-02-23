package edu.java.scrapper.data;

import edu.java.scrapper.data.dto.github.GithubRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface GithubClient {
    @GetExchange("/repos/{owner}/{repo}")
    ResponseEntity<GithubRepository> fetchRepository(@PathVariable String owner, @PathVariable String repo);
}
