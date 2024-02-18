package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.user.UserService;
import edu.java.bot.user.UserState;
import edu.java.bot.utils.LinkStorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TrackCommandTest {

    @Mock
    private LinkStorageService linkStorageService;

    @Mock
    private UserService userService;

    @Mock
    private Update update;

    @Mock
    private Message message;

    @Mock
    private User user;

    @Mock
    private Chat chat;

    @InjectMocks
    private TrackCommand trackCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(update.message()).thenReturn(message);
        when(message.from()).thenReturn(user);
        when(user.id()).thenReturn(123L);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(userService.getUserState(anyLong())).thenReturn(UserState.AWAITING_LINK);

        trackCommand = new TrackCommand(linkStorageService, userService);
    }

    @Test
    void testAwaitingLink() {
        when(message.text()).thenReturn("/track");
        when(userService.getUserState(user.id())).thenReturn(UserState.NONE);

        SendMessage response = trackCommand.handle(update);

        verify(userService, times(1)).setUserState(user.id(), UserState.AWAITING_LINK);
        Assertions.assertEquals("Пожалуйста, укажите ссылку для отслеживания.", response.getParameters().get("text"));
    }

    @Test
    void testReceivingLinkAfterCommand() {
        when(message.text()).thenReturn("https://github.com/user/repo");
        when(userService.getUserState(user.id())).thenReturn(UserState.AWAITING_LINK);

        SendMessage response = trackCommand.handle(update);

        verify(linkStorageService, times(1)).addLink(eq(user.id()), eq("https://github.com/user/repo"));
        verify(userService, times(1)).setUserState(user.id(), UserState.NONE);
        Assertions.assertEquals("Ссылка успешно добавлена для отслеживания.", response.getParameters().get("text"));
    }
}
