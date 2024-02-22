package edu.java.bot;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.domain.Link;
import static org.assertj.core.api.Assertions.*;

public class TestUtils {
    public static void checkMessage(SendMessage sendMessage, String message) {
        assertThat(sendMessage).isNotNull().extracting(SendMessage::getParameters).isNotNull()
            .extracting(p -> p.get("text")).isNotNull().isEqualTo(message);
    }

    public static Link parseLink(String link) {
        try {
            return Link.parse(link);
        } catch (Exception ignored) {
            // no-operations.
        }
        throw new AssertionError("Invalid link");
    }
}
