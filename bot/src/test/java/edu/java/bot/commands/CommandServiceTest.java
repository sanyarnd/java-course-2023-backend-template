package edu.java.bot.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CommandServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private TelegramBot bot;

    @Mock
    private Update update;

    @Mock
    private Message message;

    @Mock
    private User user;

    @Mock
    private Chat chat;

    @Mock
    private Command startCommand;

    @Mock
    private Command unknownCommand;

    private CommandService commandService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(update.message()).thenReturn(message);
        when(message.from()).thenReturn(user);
        when(user.id()).thenReturn(123L);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);

        List<Command> commands = Arrays.asList(startCommand, unknownCommand);
        commandService = new CommandService(commands, userService);
    }

    @Test
    void testNonRegisteredUserNonStartCommand() {
        when(message.text()).thenReturn("/command");
        when(userService.isRegistered(123L)).thenReturn(false);

        commandService.processUpdate(update, bot);

        verify(bot).execute(any(SendMessage.class));
    }

    @Test
    void testRegisteredUserCommand() {
        when(message.text()).thenReturn("/start");
        when(userService.isRegistered(123L)).thenReturn(true);
        when(startCommand.supports(update, userService)).thenReturn(true);

        commandService.processUpdate(update, bot);

        verify(startCommand).handle(update);
    }

    @Test
    void testNonRegisteredUserStartCommand() {
        Long userId = 123L;
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/start");
        when(message.from()).thenReturn(user);
        when(user.id()).thenReturn(userId);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(userService.isRegistered(userId)).thenReturn(false);
        when(startCommand.supports(any(Update.class), any(UserService.class))).thenReturn(true);

        commandService.processUpdate(update, bot);

        verify(startCommand, times(1)).handle(update);
    }
}
