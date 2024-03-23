package edu.java.scrapper.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.core.response.github.CommitResponse;
import edu.java.core.response.github.RepositoryResponse;
import edu.java.scrapper.data.network.GithubConnector;
import edu.java.scrapper.data.network.impl.GithubConnectorImpl;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@WireMockTest
public class GithubConnectorTest {
    private static WireMockServer server;
    private static GithubConnector client;

    @BeforeAll
    public static void init() {
        server = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());
        server.start();
        client = new GithubConnectorImpl(server.baseUrl());
        WireMock.configureFor(server.port());
    }

    @AfterAll
    public static void clean() {
        server.stop();
    }

    @Test
    public void fetchRepositoryTest() {
        var username = "AlexCawl";
        var repository = "iot-connector-application";
        stubFor(
                get(urlMatching(String.format("/repos/%s/%s", username, repository)))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", "application/json")
                                .withBody(REPOSITORY_RESPONSE_BODY))
        );

        RepositoryResponse response = client.fetchRepository(username, repository);
        Assertions.assertEquals("iot-connector-application", response.name());
        Assertions.assertEquals("AlexCawl/iot-connector-application", response.fullName());
        Assertions.assertNull(response.description());
        Assertions.assertEquals(1, response.subscribersCount());
        Assertions.assertEquals(0, response.openIssuesCount());
        Assertions.assertEquals(0, response.watchersCount());
        Assertions.assertEquals(0, response.forksCount());
    }

    @Test
    public void fetchCommitsTest() {
        var username = "AlexCawl";
        var repository = "iot-connector-application";
        stubFor(
                get(urlMatching(String.format("/repos/%s/%s/commits", username, repository)))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", "application/json")
                                .withBody(COMMITS_RESPONSE_BODY))
        );

        List<CommitResponse> response = client.fetchRepositoryCommits(username, repository);
        Assertions.assertEquals(1, response.size());
    }

    private final static String REPOSITORY_RESPONSE_BODY =
            """
                            {
                              "id": 721347253,
                              "node_id": "R_kgDOKv7itQ",
                              "name": "iot-connector-application",
                              "full_name": "AlexCawl/iot-connector-application",
                              "private": false,
                              "owner": {
                                "login": "AlexCawl",
                                "id": 91064467,
                                "node_id": "MDQ6VXNlcjkxMDY0NDY3",
                                "avatar_url": "https://avatars.githubusercontent.com/u/91064467?v=4",
                                "gravatar_id": "",
                                "url": "https://api.github.com/users/AlexCawl",
                                "html_url": "https://github.com/AlexCawl",
                                "followers_url": "https://api.github.com/users/AlexCawl/followers",
                                "following_url": "https://api.github.com/users/AlexCawl/following{/other_user}",
                                "gists_url": "https://api.github.com/users/AlexCawl/gists{/gist_id}",
                                "starred_url": "https://api.github.com/users/AlexCawl/starred{/owner}{/repo}",
                                "subscriptions_url": "https://api.github.com/users/AlexCawl/subscriptions",
                                "organizations_url": "https://api.github.com/users/AlexCawl/orgs",
                                "repos_url": "https://api.github.com/users/AlexCawl/repos",
                                "events_url": "https://api.github.com/users/AlexCawl/events{/privacy}",
                                "received_events_url": "https://api.github.com/users/AlexCawl/received_events",
                                "type": "User",
                                "site_admin": false
                              },
                              "html_url": "https://github.com/AlexCawl/iot-connector-application",
                              "description": null,
                              "fork": false,
                              "url": "https://api.github.com/repos/AlexCawl/iot-connector-application",
                              "forks_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/forks",
                              "keys_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/keys{/key_id}",
                              "collaborators_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/collaborators{/collaborator}",
                              "teams_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/teams",
                              "hooks_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/hooks",
                              "issue_events_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/issues/events{/number}",
                              "events_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/events",
                              "assignees_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/assignees{/user}",
                              "branches_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/branches{/branch}",
                              "tags_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/tags",
                              "blobs_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/git/blobs{/sha}",
                              "git_tags_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/git/tags{/sha}",
                              "git_refs_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/git/refs{/sha}",
                              "trees_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/git/trees{/sha}",
                              "statuses_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/statuses/{sha}",
                              "languages_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/languages",
                              "stargazers_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/stargazers",
                              "contributors_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/contributors",
                              "subscribers_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/subscribers",
                              "subscription_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/subscription",
                              "commits_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/commits{/sha}",
                              "git_commits_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/git/commits{/sha}",
                              "comments_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/comments{/number}",
                              "issue_comment_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/issues/comments{/number}",
                              "contents_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/contents/{+path}",
                              "compare_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/compare/{base}...{head}",
                              "merges_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/merges",
                              "archive_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/{archive_format}{/ref}",
                              "downloads_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/downloads",
                              "issues_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/issues{/number}",
                              "pulls_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/pulls{/number}",
                              "milestones_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/milestones{/number}",
                              "notifications_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/notifications{?since,all,participating}",
                              "labels_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/labels{/name}",
                              "releases_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/releases{/id}",
                              "deployments_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/deployments",
                              "created_at": "2023-11-20T21:48:47Z",
                              "updated_at": "2024-01-24T20:34:19Z",
                              "pushed_at": "2024-03-05T18:38:38Z",
                              "git_url": "git://github.com/AlexCawl/iot-connector-application.git",
                              "ssh_url": "git@github.com:AlexCawl/iot-connector-application.git",
                              "clone_url": "https://github.com/AlexCawl/iot-connector-application.git",
                              "svn_url": "https://github.com/AlexCawl/iot-connector-application",
                              "homepage": null,
                              "size": 973,
                              "stargazers_count": 0,
                              "watchers_count": 0,
                              "language": "Kotlin",
                              "has_issues": true,
                              "has_projects": true,
                              "has_downloads": true,
                              "has_wiki": false,
                              "has_pages": false,
                              "has_discussions": false,
                              "forks_count": 0,
                              "mirror_url": null,
                              "archived": false,
                              "disabled": false,
                              "open_issues_count": 0,
                              "license": null,
                              "allow_forking": true,
                              "is_template": false,
                              "web_commit_signoff_required": false,
                              "topics": [
                                        
                              ],
                              "visibility": "public",
                              "forks": 0,
                              "open_issues": 0,
                              "watchers": 0,
                              "default_branch": "master",
                              "temp_clone_token": null,
                              "network_count": 0,
                              "subscribers_count": 1
                            }
                    """;

    private final static String COMMITS_RESPONSE_BODY =
            """
                            [
                              {
                                "sha": "3a31e76b7b1f6e68739cd1b5c73cbf2ef081d7e1",
                                "node_id": "C_kwDOKv7itdoAKDNhMzFlNzZiN2IxZjZlNjg3MzljZDFiNWM3M2NiZjJlZjA4MWQ3ZTE",
                                "commit": {
                                  "author": {
                                    "name": "AlexCawl",
                                    "email": "babushkinsusu@gmail.com",
                                    "date": "2024-03-05T18:38:21Z"
                                  },
                                  "committer": {
                                    "name": "AlexCawl",
                                    "email": "babushkinsusu@gmail.com",
                                    "date": "2024-03-05T18:38:21Z"
                                  },
                                  "message": "fix: fixed README.md",
                                  "tree": {
                                    "sha": "bb340ef5041b3eec9f5f4edbbe8b22d5b1c6b912",
                                    "url": "https://api.github.com/repos/AlexCawl/iot-connector-application/git/trees/bb340ef5041b3eec9f5f4edbbe8b22d5b1c6b912"
                                  },
                                  "url": "https://api.github.com/repos/AlexCawl/iot-connector-application/git/commits/3a31e76b7b1f6e68739cd1b5c73cbf2ef081d7e1",
                                  "comment_count": 0,
                                  "verification": {
                                    "verified": false,
                                    "reason": "unsigned",
                                    "signature": null,
                                    "payload": null
                                  }
                                },
                                "url": "https://api.github.com/repos/AlexCawl/iot-connector-application/commits/3a31e76b7b1f6e68739cd1b5c73cbf2ef081d7e1",
                                "html_url": "https://github.com/AlexCawl/iot-connector-application/commit/3a31e76b7b1f6e68739cd1b5c73cbf2ef081d7e1",
                                "comments_url": "https://api.github.com/repos/AlexCawl/iot-connector-application/commits/3a31e76b7b1f6e68739cd1b5c73cbf2ef081d7e1/comments",
                                "author": {
                                  "login": "AlexCawl",
                                  "id": 91064467,
                                  "node_id": "MDQ6VXNlcjkxMDY0NDY3",
                                  "avatar_url": "https://avatars.githubusercontent.com/u/91064467?v=4",
                                  "gravatar_id": "",
                                  "url": "https://api.github.com/users/AlexCawl",
                                  "html_url": "https://github.com/AlexCawl",
                                  "followers_url": "https://api.github.com/users/AlexCawl/followers",
                                  "following_url": "https://api.github.com/users/AlexCawl/following{/other_user}",
                                  "gists_url": "https://api.github.com/users/AlexCawl/gists{/gist_id}",
                                  "starred_url": "https://api.github.com/users/AlexCawl/starred{/owner}{/repo}",
                                  "subscriptions_url": "https://api.github.com/users/AlexCawl/subscriptions",
                                  "organizations_url": "https://api.github.com/users/AlexCawl/orgs",
                                  "repos_url": "https://api.github.com/users/AlexCawl/repos",
                                  "events_url": "https://api.github.com/users/AlexCawl/events{/privacy}",
                                  "received_events_url": "https://api.github.com/users/AlexCawl/received_events",
                                  "type": "User",
                                  "site_admin": false
                                },
                                "committer": {
                                  "login": "AlexCawl",
                                  "id": 91064467,
                                  "node_id": "MDQ6VXNlcjkxMDY0NDY3",
                                  "avatar_url": "https://avatars.githubusercontent.com/u/91064467?v=4",
                                  "gravatar_id": "",
                                  "url": "https://api.github.com/users/AlexCawl",
                                  "html_url": "https://github.com/AlexCawl",
                                  "followers_url": "https://api.github.com/users/AlexCawl/followers",
                                  "following_url": "https://api.github.com/users/AlexCawl/following{/other_user}",
                                  "gists_url": "https://api.github.com/users/AlexCawl/gists{/gist_id}",
                                  "starred_url": "https://api.github.com/users/AlexCawl/starred{/owner}{/repo}",
                                  "subscriptions_url": "https://api.github.com/users/AlexCawl/subscriptions",
                                  "organizations_url": "https://api.github.com/users/AlexCawl/orgs",
                                  "repos_url": "https://api.github.com/users/AlexCawl/repos",
                                  "events_url": "https://api.github.com/users/AlexCawl/events{/privacy}",
                                  "received_events_url": "https://api.github.com/users/AlexCawl/received_events",
                                  "type": "User",
                                  "site_admin": false
                                },
                                "parents": [
                                  {
                                    "sha": "e75e362ec9b4fd03cad24839081720cbe5f7931e",
                                    "url": "https://api.github.com/repos/AlexCawl/iot-connector-application/commits/e75e362ec9b4fd03cad24839081720cbe5f7931e",
                                    "html_url": "https://github.com/AlexCawl/iot-connector-application/commit/e75e362ec9b4fd03cad24839081720cbe5f7931e"
                                  }
                                ]
                              }
                            ]
                    """;
}
