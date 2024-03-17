package edu.java.scrapper.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.scrapper.data.network.StackOverflowClient;
import edu.java.scrapper.data.network.impl.StackOverflowClientImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

@WireMockTest
public class StackOverflowClientTest {
    private static WireMockServer server;
    private static StackOverflowClient client;

    @BeforeAll
    public static void init() {
        server = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());
        server.start();
        client = new StackOverflowClientImpl(server.baseUrl());
        WireMock.configureFor(server.port());
    }

    @AfterAll
    public static void clean() {
        server.stop();
    }

    @Test
    public void fetchRepositoryTest() {
        var questionId = "249231";
        var body = """
                {
                    "items": [
                      {
                        "owner": {
                          "account_id": 202054,
                          "reputation": 24652,
                          "user_id": 448875,
                          "user_type": "registered",
                          "profile_image": "https://www.gravatar.com/avatar/08bf88dd3de014943401fa7879763a4f?s=256&d=identicon&r=PG",
                          "display_name": "broot",
                          "link": "https://stackoverflow.com/users/448875/broot"
                        },
                        "is_accepted": false,
                        "score": 3,
                        "last_activity_date": 1708686651,
                        "last_edit_date": 1708686651,
                        "creation_date": 1708686218,
                        "answer_id": 78046932,
                        "question_id": 78046340,
                        "content_license": "CC BY-SA 4.0"
                      },
                      {
                        "owner": {
                          "account_id": 15144230,
                          "reputation": 2691,
                          "user_id": 10928439,
                          "user_type": "registered",
                          "profile_image": "https://www.gravatar.com/avatar/ede5fd9e9ee4f6c685b19960d57ada05?s=256&d=identicon&r=PG&f=y&so-version=2",
                          "display_name": "Simon Jacobs",
                          "link": "https://stackoverflow.com/users/10928439/simon-jacobs"
                        },
                        "is_accepted": true,
                        "score": 1,
                        "last_activity_date": 1708682234,
                        "creation_date": 1708682234,
                        "answer_id": 78046513,
                        "question_id": 78046340,
                        "content_license": "CC BY-SA 4.0"
                      }
                    ],
                    "has_more": false,
                    "quota_max": 300,
                    "quota_remaining": 204
                  }
            """;
        stubFor(
            get(urlEqualTo(String.format("/questions/%s/answers?site=stackoverflow", questionId)))
                .willReturn(aResponse()
                    .withStatus(HttpStatus.OK.value())
                    .withHeader("Content-Type", "application/json")
                    .withBody(body))
        );

        var response = client.fetchAnswers(questionId);
        Assertions.assertEquals(2, response.answers().size());
        Assertions.assertEquals("broot", response.answers().get(0).owner().displayName());
        Assertions.assertEquals(1708682234, response.answers().get(1).creationDate());
    }
}
