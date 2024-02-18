package edu.java.bot;

import edu.java.bot.util.Links;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LinksTest {

    @Test
    void testIsValid_ValidLink_ReturnsTrue() {
        String validLink = "https://github.com/user/repo";
        boolean isValid = Links.isValid(validLink);
        Assertions.assertTrue(isValid);
    }

    @Test
    void testIsValid_InvalidLink_ReturnsFalse() {
        String invalidLink = "https://example.com";
        boolean isValid = Links.isValid(invalidLink);
        Assertions.assertFalse(isValid);
    }

    @Test
    void testSplitByDomain_ValidLinks_ReturnsMapWithDomains() {
        List<String> links = Arrays.asList(
            "https://github.com/user1/repo1",
            "https://stackoverflow.com/questions/123",
            "https://github.com/user2/repo2"
        );

        Map<String, List<String>> result = Links.splitByDomain(links);

        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.containsKey("github.com"));
        Assertions.assertTrue(result.containsKey("stackoverflow.com"));
        Assertions.assertEquals(2, result.get("github.com").size());
        Assertions.assertEquals(1, result.get("stackoverflow.com").size());
    }

    @Test
    void testSplitByDomain_NullLinks_ReturnsNull() {
        List<String> links = null;

        Map<String, List<String>> result = Links.splitByDomain(links);

        Assertions.assertNull(result);
    }
}
