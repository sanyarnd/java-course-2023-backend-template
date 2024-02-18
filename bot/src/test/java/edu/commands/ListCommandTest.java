package edu.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.services.tracking.TrackingService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ListCommand.class)
public class ListCommandTest {

    @MockBean
    private TrackingService trackingService;

    @Test
    public void testHandle_emptyList() {
        Update update = mock(Update.class, RETURNS_DEEP_STUBS);
        when(update.message().chat().id()).thenReturn(123L);

        ListCommand command = new ListCommand(trackingService);

        when(trackingService.getTrackings(anyLong())).thenReturn(Collections.emptyList());

        SendMessage result = command.handle(update);

        assertThat(result.getParameters().get("text").toString()).isEqualTo("Нет отслеживаемых ссылок");
    }

    @Test
    public void testHandle_nonEmptyList() {
        Update update = mock(Update.class, RETURNS_DEEP_STUBS);
        when(update.message().chat().id()).thenReturn(123L);

        ListCommand command = new ListCommand(trackingService);

        when(trackingService.getTrackings(anyLong())).thenReturn(List.of("http://example.com"));

        SendMessage result = command.handle(update);

        assertThat(result.getParameters().get("text").toString()).isEqualTo("Список url:\nhttp://example.com");
    }
}
