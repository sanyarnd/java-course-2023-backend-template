package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HelpCommandTest {

    @Mock
    private List<Command> commands;
    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private Chat chat;

    private HelpCommand helpCommand;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(12345L);

        Command trackCommand = mock(Command.class);
        when(trackCommand.command()).thenReturn("/track");
        when(trackCommand.description()).thenReturn("Начать отслеживание ссылки.");

        Command listCommand = mock(Command.class);
        when(listCommand.command()).thenReturn("/list");
        when(listCommand.description()).thenReturn("Показать список отслеживаемых ссылок.");

        commands = Arrays.asList(trackCommand, listCommand);

        helpCommand = new HelpCommand(commands);
    }

    @Test
    public void testHandle() {
        String expectedMessage = """
            Список поддерживаемых команд:
            /track - Начать отслеживание ссылки.
            /list - Показать список отслеживаемых ссылок.""";

        SendMessage result = helpCommand.handle(update);

        Assertions.assertEquals(expectedMessage, result.getParameters().get("text"));
        Assertions.assertEquals(12345L, result.getParameters().get("chat_id"));
    }
}
