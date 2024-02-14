package edu.java.bot.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.comand.TrackCommand;
import edu.java.bot.service.impl.UpdateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TrackCommandTest {

    @Mock
    private UpdateService updateService;
    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private Chat chat;

    @InjectMocks
    private TrackCommand trackCommand;

    private final Long chatId = 123L;

    @BeforeEach
    public void setUp() {
        given(update.message()).willReturn(message);
        given(message.chat()).willReturn(chat);
        given(chat.id()).willReturn(chatId);
    }

    @Test
    void testTrackCommandWithoutURL() {
        given(message.text()).willReturn("/track");

        SendMessage response = trackCommand.handle(update);
        Long id = (Long) response.getParameters().get("chat_id");
        String text = (String) response.getParameters().get("text");
        assertThat(id).isEqualTo(chatId);
        assertThat(text).isEqualTo("Usage: /track <URL>");
    }

    @Test
    void testTrackCommandWithSupportedURL() {
        String supportedURL = "http://example.com";
        given(message.text()).willReturn("/track " + supportedURL);
        given(updateService.checkResourceURL(anyLong(), eq(supportedURL))).willReturn(true);

        SendMessage response = trackCommand.handle(update);

        Long id = (Long) response.getParameters().get("chat_id");
        String text = (String) response.getParameters().get("text");
        assertThat(id).isEqualTo(chatId);
        assertThat(text).isEqualTo("Started tracking: " + supportedURL);
    }

    @Test
    void testTrackCommandWithUnsupportedURL() {
        String unsupportedURL = "http://unsupported.com";
        given(message.text()).willReturn("/track " + unsupportedURL);
        given(updateService.checkResourceURL(anyLong(), eq(unsupportedURL))).willReturn(false);

        SendMessage response = trackCommand.handle(update);

        Long id = (Long) response.getParameters().get("chat_id");
        String text = (String) response.getParameters().get("text");
        assertThat(id).isEqualTo(chatId);
        assertThat(text).isEqualTo("This URL is not supported for tracking.");
    }
}
