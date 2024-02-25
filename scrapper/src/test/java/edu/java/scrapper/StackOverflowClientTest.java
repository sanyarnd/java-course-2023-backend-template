package edu.java.scrapper;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import edu.java.scrapper.client.StackOverflowClient;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StackOverflowClientTest {
    @Rule
    public final WireMockRule wireMockRule = new WireMockRule();
    private final String baseUrl = "http://localhost:8080";
    private StackOverflowClient stackOverflowClient;
    private final String REPOSITORY_PATH = "src" + File.separator + "test" +
        File.separator + "java" + File.separator + "edu" + File.separator + "java" + File.separator + "scrapper" +
        File.separator + "question.json";

    @Before
    public void initClient() {
        stackOverflowClient = new StackOverflowClient(baseUrl);
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

