package edu.java.scrapper;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
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

public class GitHubClientTest {
    @Rule
    public final WireMockRule wireMockRule = new WireMockRule();
    private final String baseUrl = "http://localhost:8080";
    private GitHubClient gitHubClient;
    private final String REPOSITORY_PATH = "src" + File.separator + "test" +
        File.separator + "java" + File.separator + "edu" + File.separator + "java" + File.separator + "scrapper" +
        File.separator + "repository.json";

    @Before
    public void initClient() {
        gitHubClient = new GitHubClient(baseUrl);
    }

    @Test
    public void getRepository_shouldGetInfoAboutRepo() throws IOException {
        //arrange
        File file = new File(REPOSITORY_PATH);
        String repositoryJson = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        String url = "/repos/Minkerr/Tinkoff_Backend_Course_Application";
        stubFor(get(url)
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(repositoryJson))
        );
        //act
        var act = gitHubClient.getRepository("Minkerr", "Tinkoff_Backend_Course_Application");
        //assert
        assertThat(act.id()).isEqualTo(754620859);
        assertThat(act.repositoryName()).isEqualTo("Tinkoff_Backend_Course_Application");
        assertThat(act.pushedAt()).isEqualTo("2024-02-23T18:56:38Z");
    }

}
