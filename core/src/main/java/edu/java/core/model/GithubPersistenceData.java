package edu.java.core.model;

import edu.java.core.response.github.CommitResponse;
import edu.java.core.response.github.RepositoryResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GithubPersistenceData {
    private Integer forksCount;
    private Integer commitsCount;
    private Integer subscribersCount;
    private Integer openIssuesCount;
    private Integer watchersCount;

    public GithubPersistenceData(
            RepositoryResponse repositoryResponse,
            List<CommitResponse> commitResponse
    ) {
        this.forksCount = repositoryResponse.forksCount();
        this.commitsCount = commitResponse.size();
        this.subscribersCount = repositoryResponse.subscribersCount();
        this.openIssuesCount = repositoryResponse.openIssuesCount();
        this.watchersCount = repositoryResponse.watchersCount();
    }
}
