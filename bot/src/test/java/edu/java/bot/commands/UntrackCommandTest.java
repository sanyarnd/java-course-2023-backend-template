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

class UntrackCommandTest {

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
    private UntrackCommand untrackCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(update.message()).thenReturn(message);
        when(message.from()).thenReturn(user);
        when(user.id()).thenReturn(123L);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);

        untrackCommand = new UntrackCommand(linkStorageService, userService);
    }

    @Test
    void testAwaitingLinkInput() {
        when(message.text()).thenReturn("/untrack");
        when(userService.getUserState(anyLong())).thenReturn(UserState.NONE);

        SendMessage response = untrackCommand.handle(update);

        verify(userService, times(1)).setUserState(anyLong(), eq(UserState.AWAITING_UNTRACK_LINK));
        Assertions.assertEquals(
            "Пожалуйста, укажите ссылку или 'all' для удаления.",
            response.getParameters().get("text")
        );
    }
}
