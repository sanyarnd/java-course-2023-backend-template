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
import static org.mockito.Mockito.when;

class UnknownCommandTest {

    @Mock
    private Update update;

    @Mock
    private Message message;

    @Mock
    private Chat chat;

    private UnknownCommand unknownCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);

        unknownCommand = new UnknownCommand();
    }

    @Test
    void testUnknownCommandResponse() {
        SendMessage response = unknownCommand.handle(update);

        String expectedText = "Такой команды не существует. Введите /help для списка доступных команд.";

        Assertions.assertEquals(
            expectedText, response.getParameters().get("text"));
    }
}
