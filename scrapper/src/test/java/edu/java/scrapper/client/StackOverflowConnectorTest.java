package edu.java.scrapper.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.core.response.stackoverflow.Answer;
import edu.java.core.response.stackoverflow.AnswerResponse;
import edu.java.core.response.stackoverflow.Comment;
import edu.java.core.response.stackoverflow.CommentResponse;
import edu.java.scrapper.data.network.StackOverflowConnector;
import edu.java.scrapper.data.network.impl.StackOverflowConnectorImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@WireMockTest
public class StackOverflowConnectorTest {
    private static WireMockServer server;
    private static StackOverflowConnector client;

    @BeforeAll
    public static void init() {
        server = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());
        server.start();
        client = new StackOverflowConnectorImpl(server.baseUrl());
        WireMock.configureFor(server.port());
    }

    @AfterAll
    public static void clean() {
        server.stop();
    }

    @Test
    public void fetchQuestionAnswersTest() {
        var questionId = "78200710";
        stubFor(
                get(urlEqualTo(String.format("/questions/%s/answers?site=stackoverflow", questionId)))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", "application/json")
                                .withBody(ANSWERS_RESPONSE_BODY))
        );

        AnswerResponse response = client.fetchAnswers(questionId);
        Assertions.assertEquals(1, response.answers().size());
        Answer answer = response.answers().get(0);
        Assertions.assertEquals("Osuwariboy", answer.owner().displayName());
        Assertions.assertEquals(0, answer.score());
    }

    @Test
    public void fetchQuestionCommentsTest() {
        var questionId = "78200710";
        stubFor(
                get(urlEqualTo(String.format("/questions/%s/comments?site=stackoverflow", questionId)))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", "application/json")
                                .withBody(COMMENTS_RESPONSE_BODY))
        );

        CommentResponse response = client.fetchComments(questionId);
        Assertions.assertEquals(2, response.comments().size());
        Comment comment = response.comments().get(1);
        Assertions.assertEquals("apokryfos", comment.owner().displayName());
        Assertions.assertEquals(0, comment.score());
    }

    private final static String ANSWERS_RESPONSE_BODY = """
            {
              "items": [
                {
                  "owner": {
                    "account_id": 1477553,
                    "reputation": 1359,
                    "user_id": 1387606,
                    "user_type": "registered",
                    "accept_rate": 70,
                    "profile_image": "https://www.gravatar.com/avatar/4b573dfb1d444d7eff698c6aeba5442b?s=256&d=identicon&r=PG",
                    "display_name": "Osuwariboy",
                    "link": "https://stackoverflow.com/users/1387606/osuwariboy"
                  },
                  "is_accepted": false,
                  "score": 0,
                  "last_activity_date": 1711034799,
                  "creation_date": 1711034799,
                  "answer_id": 78201095,
                  "question_id": 78200710,
                  "content_license": "CC BY-SA 4.0"
                }
              ],
              "has_more": false,
              "quota_max": 10000,
              "quota_remaining": 9983
            }
            """;

    private final static String COMMENTS_RESPONSE_BODY = """
            {
              "items": [
                {
                  "owner": {
                    "account_id": 1477553,
                    "reputation": 1359,
                    "user_id": 1387606,
                    "user_type": "registered",
                    "accept_rate": 70,
                    "profile_image": "https://www.gravatar.com/avatar/4b573dfb1d444d7eff698c6aeba5442b?s=256&d=identicon&r=PG",
                    "display_name": "Osuwariboy",
                    "link": "https://stackoverflow.com/users/1387606/osuwariboy"
                  },
                  "reply_to_user": {
                    "account_id": 226653,
                    "reputation": 39656,
                    "user_id": 487813,
                    "user_type": "registered",
                    "accept_rate": 84,
                    "profile_image": "https://i.stack.imgur.com/lU5if.jpg?s=256&g=1",
                    "display_name": "apokryfos",
                    "link": "https://stackoverflow.com/users/487813/apokryfos"
                  },
                  "edited": false,
                  "score": 0,
                  "creation_date": 1711033925,
                  "post_id": 78200710,
                  "comment_id": 137865564,
                  "content_license": "CC BY-SA 4.0"
                },
                {
                  "owner": {
                    "account_id": 226653,
                    "reputation": 39656,
                    "user_id": 487813,
                    "user_type": "registered",
                    "accept_rate": 84,
                    "profile_image": "https://i.stack.imgur.com/lU5if.jpg?s=256&g=1",
                    "display_name": "apokryfos",
                    "link": "https://stackoverflow.com/users/487813/apokryfos"
                  },
                  "edited": false,
                  "score": 0,
                  "creation_date": 1711032967,
                  "post_id": 78200710,
                  "comment_id": 137865364,
                  "content_license": "CC BY-SA 4.0"
                }
              ],
              "has_more": false,
              "quota_max": 10000,
              "quota_remaining": 9998
            }
            """;
}
