package edu.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.UntrackCommand;
import edu.java.bot.services.tracking.TrackingService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = UntrackCommand.class)
public class UntrackCommandTest {

    @MockBean
    private TrackingService trackingService;

    @Test
    public void testHandle_noUrl() {
        Update update = mock(Update.class, RETURNS_DEEP_STUBS);
        when(update.message().text()).thenReturn("/untrack");

        UntrackCommand command = new UntrackCommand(trackingService);

        SendMessage result = command.handle(update);

        assertThat(result.getParameters().get("text").toString()).isEqualTo("Необходимо указать url");
    }

    @Test
    public void testHandle_existingUrl() {
        Update update = mock(Update.class, RETURNS_DEEP_STUBS);
        when(update.message().text()).thenReturn("/untrack http://example.com");

        UntrackCommand command = new UntrackCommand(trackingService);

        when(trackingService.removeTracking(anyLong(), anyString())).thenReturn(true);

        SendMessage result = command.handle(update);

        assertThat(result.getParameters().get("text").toString()).isEqualTo("Удален url: http://example.com");
    }

    @Test
    public void testHandle_nonExistingUrl() {
        Update update = mock(Update.class, RETURNS_DEEP_STUBS);
        when(update.message().text()).thenReturn("/untrack http://example.com");

        UntrackCommand command = new UntrackCommand(trackingService);

        when(trackingService.removeTracking(anyLong(), anyString())).thenReturn(false);

        SendMessage result = command.handle(update);

        assertThat(result.getParameters().get("text").toString()).isEqualTo("Такого url нет в списке отслеживаемых");
    }
}
