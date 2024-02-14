package edu.java.bot.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.comand.UntrackCommand;
import edu.java.bot.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UntrackCommandTest {

    private final UrlRepository urlRepository = UrlRepository.getInstance();
    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private Chat chat;

    @InjectMocks
    private UntrackCommand untrackCommand;

    private final Long chatId = 123L;

    @BeforeEach
    public void setUp() {
        given(update.message()).willReturn(message);
        given(message.chat()).willReturn(chat);
        given(chat.id()).willReturn(chatId);
    }

    @Test
    void testUntrackCommandWithoutURL() {
        given(message.text()).willReturn("/untrack");

        SendMessage response = untrackCommand.handle(update);

        Long id = (Long) response.getParameters().get("chat_id");
        String text = (String) response.getParameters().get("text");
        assertThat(id).isEqualTo(chatId);
        assertThat(text).isEqualTo("Usage: /untrack <URL>");
    }

    @Test
    void testUntrackCommandWithExistingURL() {
        String existingURL = "http://example.com";
        given(message.text()).willReturn("/untrack " + existingURL);
        urlRepository.addUrl(chatId, existingURL);

        SendMessage response = untrackCommand.handle(update);

        Long id = (Long) response.getParameters().get("chat_id");
        String text = (String) response.getParameters().get("text");
        assertThat(id).isEqualTo(chatId);
        assertThat(text).isEqualTo("Untracking: " + existingURL);
    }

    @Test
    void testUntrackCommandWithNonexistentURL() {
        String nonexistentURL = "http://notfound.com";
        given(message.text()).willReturn("/untrack " + nonexistentURL);

        SendMessage response = untrackCommand.handle(update);

        Long id = (Long) response.getParameters().get("chat_id");
        String text = (String) response.getParameters().get("text");
        assertThat(id).isEqualTo(chatId);
        assertThat(text).isEqualTo("This url was not found");
    }
}
