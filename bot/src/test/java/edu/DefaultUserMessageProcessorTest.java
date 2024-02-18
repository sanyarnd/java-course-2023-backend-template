package edu;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.commands.Command;
import edu.java.bot.services.DefaultUserMessageProcessor;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = DefaultUserMessageProcessor.class)
public class DefaultUserMessageProcessorTest {

    @Test
    public void testProcess() {
        Command command = mock(Command.class);
        DefaultUserMessageProcessor defaultUserMessageProcessor = new DefaultUserMessageProcessor(List.of(command));
        Update update = mock(Update.class, RETURNS_DEEP_STUBS);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);

        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/help");
        when(command.supports(update)).thenReturn(true);
        when(command.handle(update)).thenReturn(null);

        defaultUserMessageProcessor.process(update);

        verify(command, times(1)).supports(update);
        verify(command, times(1)).handle(update);
    }

    @Test
    public void testProcessWhenNoOneSupportReturnsSpecialMessage() {
        Command command = mock(Command.class);
        var defaultUserMessageProcessor = new DefaultUserMessageProcessor(
            List.of(command, command, command)
        );
        Update update = mock(Update.class, RETURNS_DEEP_STUBS);

        when(command.supports(update)).thenReturn(false);

        var answer = defaultUserMessageProcessor.process(update);

        assertThat(answer.getParameters().get("text").toString()).isEqualTo("Неизвестная команда");
    }
}
