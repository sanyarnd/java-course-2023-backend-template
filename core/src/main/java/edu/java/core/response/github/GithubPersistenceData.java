package edu.java.core.response.github;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class GithubPersistenceData {
    private Integer forksCount;
    private Integer commitsCount;
    private Integer subscribersCount;
    private Integer openIssuesCount;
    private Integer watchersCount;
}
