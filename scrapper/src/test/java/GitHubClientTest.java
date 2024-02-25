import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.scrapper.client.GitHubClient;
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
public class GitHubClientTest {
    private WireMockServer wireMockServer;
    private final String baseUrl = "http://localhost:8080";
    private GitHubClient gitHubClient;
    private final String REPOSITORY_PATH = "src" + File.separator + "test" +
        File.separator + "java" + File.separator + "edu" + File.separator + "java" + File.separator + "scrapper" +
        File.separator + "repository.json";

    @BeforeAll
    public void setUp() {
        gitHubClient = new GitHubClient(baseUrl);
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
    }

    @AfterAll
    public void tearDown() {
        wireMockServer.stop();
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
