package edu.java.bot.command;

import edu.java.bot.TestUtils;
import edu.java.bot.domain.Link;
import edu.java.bot.service.LinkService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LinksCommandsBaseTest {
    @Mock
    protected LinkService linkService;
    protected static final List<String> LINKS = List.of(
        "https://stackoverflow.com/questions/927358/how-do-i-undo-the-most-recent-local-commits-in-git",
        "https://stackoverflow.com/questions/2003505/how-do-i-delete-a-git-branch-locally-and-remotely",
        "https://stackoverflow.com/questions/292357/what-is-the-difference-between-git-pull-and-git-fetch",
        "https://stackoverflow.com/questions/477816/which-json-content-type-do-i-use",
        "https://stackoverflow.com/questions/348170/how-do-i-undo-git-add-before-commit",
        "https://github.com/spring-projects/spring-framework",
        "https://github.com/hibernate/hibernate-orm",
        "https://junit.org/junit5/docs/current/user-guide/#writing-tests-annotations",
        "https://hub.docker.com/_/postgres",
        "https://www.google.com/"
    );

    protected static final String INVALID_LINK_MSG = "The link is not correct.";

    protected void setUntracked(long chatId, String link) {
        lenient().when(linkService.find(chatId, link)).thenReturn(Optional.empty());
    }

    protected void setAllUntracked(long chatId) {
        lenient().when(linkService.findAll(chatId)).thenReturn(List.of());
    }

    protected void setTracked(long chatId, String link) {
        try {
            lenient().when(linkService.find(chatId, link)).thenReturn(Optional.of(Link.parse(link)));
        } catch (Exception ignored) {
            // no-operations.
        }
    }

    protected void setAllTracked(long chatId, String... links) {
        lenient().when(linkService.findAll(chatId))
            .thenReturn(Arrays.stream(links).map(TestUtils::parseLink).collect(Collectors.toList()));
        lenient().when(linkService.find(eq(chatId), any(String.class))).thenAnswer(invocation -> {
            String link = invocation.getArgument(1);
            return Arrays.stream(links).filter(link::equals).findFirst().map(TestUtils::parseLink);
        });
    }

    protected void setSupported(String... domains) {
        lenient().when(linkService.isSupported(any(Link.class))).thenAnswer(invocation -> {
            Link link = invocation.getArgument(0);
            return Arrays.stream(domains).anyMatch(d -> link.getDomain().equals(d));
        });
        lenient().when(linkService.getSupportedDomains()).thenReturn(Arrays.asList(domains));
    }

    protected void setAllSupported() {
        lenient().when(linkService.isSupported(any(Link.class))).thenReturn(true);
    }
}
