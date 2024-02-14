package edu.java.bot.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.comand.ListCommand;
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
class ListCommandTest {

    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private Chat chat;
    @InjectMocks
    private ListCommand listCommand;

    private final UrlRepository urlRepository = UrlRepository.getInstance();
    private final Long chatId = 123L;

    @BeforeEach
    public void setUp() {
        given(update.message()).willReturn(message);
        given(message.chat()).willReturn(chat);
        given(chat.id()).willReturn(chatId);
    }

    @Test
    void testListCommandWithNonEmptyUrls() {
        urlRepository.addUrl(chatId, "http://example.com");
        urlRepository.addUrl(chatId, "http://example.org");

        SendMessage result = listCommand.handle(update);

        String text = (String) result.getParameters().get("text");
        assertThat(text).isEqualTo("http://example.org\nhttp://example.com\n");
    }

    @Test
    void testListCommandWithEmptyUrls() {
        SendMessage result = listCommand.handle(update);

        String text = (String) result.getParameters().get("text");
        assertThat(text).isEqualTo("You don't have any tracking links");
    }
}
