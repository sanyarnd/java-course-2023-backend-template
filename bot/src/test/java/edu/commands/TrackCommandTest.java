package edu.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.services.tracking.TrackingService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TrackCommand.class)
public class TrackCommandTest {

    @MockBean
    private TrackingService trackingService;

    @Test
    public void testHandle_noUrl() {
        Update update = mock(Update.class, RETURNS_DEEP_STUBS);
        when(update.message().text()).thenReturn("/track");

        TrackCommand command = new TrackCommand(trackingService);

        SendMessage result = command.handle(update);

        assertThat(result.getParameters().get("text").toString()).isEqualTo("Необходимо указать url");
    }

    @Test
    public void testHandle_newUrl() {
        Update update = mock(Update.class, RETURNS_DEEP_STUBS);
        when(update.message().text()).thenReturn("/track http://example.com");

        TrackCommand command = new TrackCommand(trackingService);

        when(trackingService.addTracking(anyLong(), anyString())).thenReturn(true);

        SendMessage result = command.handle(update);

        assertThat(result.getParameters().get("text").toString()).isEqualTo("Добавлен новый url: http://example.com");
    }

    @Test
    public void testHandle_existingUrl() {
        Update update = mock(Update.class, RETURNS_DEEP_STUBS);
        when(update.message().text()).thenReturn("/track http://example.com");

        TrackCommand command = new TrackCommand(trackingService);

        when(trackingService.addTracking(anyLong(), anyString())).thenReturn(false);

        SendMessage result = command.handle(update);

        assertThat(result.getParameters().get("text").toString()).isEqualTo("Такой url уже отслеживается");
    }
}
