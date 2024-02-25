package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.dto.stackoverflow.Answer;
import edu.java.dto.stackoverflow.Owner;
import edu.java.services.StackOverflowService;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class StackOverflowServiceTest {
    private WireMockServer wireMockServer;
    private StackOverflowService stackOverflowService;

    @BeforeEach
    void init() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
        stackOverflowService =
            new StackOverflowService(WebClient.create("http://localhost:" + wireMockServer.port()));
    }

    @AfterEach
    void stop() {
        wireMockServer.stop();
    }

    @Test
    void getNewAnswersFromStackOverflowTest() {
        long questionId = 2003505L;
        OffsetDateTime from = OffsetDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);

        String responseBody = """
            [
              {
                "answer_id": 123,
                "question_id": 2003505,
                "last_activity_date": "2022-01-01T10:00:00Z",
                "link": "https://stackoverflow.com/answer/123",
                "owner": {
                  "user_id": 456,
                  "display_name": "John Doe"
                }
              },
              {
                "answer_id": 789,
                "question_id": 2003505,
                "last_activity_date": "2022-01-02T15:30:00Z",
                "link": "https://stackoverflow.com/answer/789",
                "owner": {
                  "user_id": 987,
                  "display_name": "Jane Smith"
                }
              }
            ]
            """;

        var uri = UriComponentsBuilder
            .fromPath("/questions/{questionId}/answers")
            .queryParam("order", "desc")
            .queryParam("min", from.toEpochSecond())
            .queryParam("sort", "activity")
            .queryParam("site", "stackoverflow")
            .uriVariables(Map.of("questionId", questionId));

        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo(uri.toUriString()))
            .willReturn(WireMock.aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(responseBody)
            )
        );

        Flux<Answer> response = stackOverflowService.getNewAnswersFromStackOverflow(questionId, from);

        StepVerifier.create(response)
            .expectNextMatches(answer -> {
                Owner owner = answer.getOwner();
                return answer.getId() == 123 &&
                    answer.getQuestionId() == 2003505 &&
                    answer.getActivityDate().isEqual(OffsetDateTime.parse("2022-01-01T10:00:00Z")) &&
                    answer.getLink().equals("https://stackoverflow.com/answer/123") &&
                    owner.getId() == 456 &&
                    owner.getName().equals("John Doe");
            })
            .expectNextMatches(answer -> {
                Owner owner = answer.getOwner();
                return answer.getId() == 789 &&
                    answer.getQuestionId() == 2003505 &&
                    answer.getActivityDate().isEqual(OffsetDateTime.parse("2022-01-02T15:30:00Z")) &&
                    answer.getLink().equals("https://stackoverflow.com/answer/789") &&
                    owner.getId() == 987 &&
                    owner.getName().equals("Jane Smith");
            })
            .verifyComplete();
    }
}

