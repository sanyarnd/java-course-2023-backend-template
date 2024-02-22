package edu.java.bot;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import java.util.Random;
import static org.mockito.Mockito.*;

public class UpdateMock {
    private final Update update;
    private final Random random;
    private Message message;

    public UpdateMock() {
        update = mock(Update.class);
        random = new Random();
        when(update.message()).thenAnswer(invocation -> {
            if (message == null) {
                message = mock(Message.class);
                when(update.message()).thenReturn(message);
            }
            return message;
        });
    }

    public Update build() {
        return update;
    }

    public UpdateMock withMessage(String text) {
        when(update.message().text()).thenReturn(text);
        return this;
    }

    public UpdateMock withChat(long id) {
        Chat chat = mock(Chat.class);
        when(update.message().chat()).thenReturn(chat);
        when(chat.id()).thenReturn(id);
        return this;
    }

    public UpdateMock withChat() {
        return withChat(random.nextLong());
    }
}
