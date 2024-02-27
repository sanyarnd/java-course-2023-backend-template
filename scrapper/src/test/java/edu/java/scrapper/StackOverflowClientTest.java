package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.scrapper.client.StackOverflowClient;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StackOverflowClientTest {
    private static WireMockServer wireMockServer;
    private static StackOverflowClient stackOverflowClient;
    private static final String baseUrl = "http://localhost:8080";
    private static final String REPOSITORY_PATH = "src" + File.separator + "test" +
        File.separator + "resources" + File.separator + "question.json";

    @BeforeAll
    public static void setUp() {
        stackOverflowClient = new StackOverflowClient(baseUrl);
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void getQuestion_shouldGetInfoAboutQuestion() throws IOException {
        //arrange
        File file = new File(REPOSITORY_PATH);
        String repositoryJson = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        String url = "/2.3/questions/23584996?order=desc&sort=activity&site=stackoverflow";
        stubFor(get(url)
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(repositoryJson))
        );
        //act
        var act = stackOverflowClient.getQuestion(23584996).items().get(0);
        //assert
        assertThat(act.id()).isEqualTo(23584996);
        assertThat(act.title()).isEqualTo("How to write a Hello World application in Java?");
        assertThat(act.lastEditDate()).isEqualTo("2014-05-23T22:02:28Z");
    }

}

