package edu.java.bot;


import com.google.gson.Gson;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import edu.java.bot.message_sender.TelegramMessageSender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TelegramMessageSenderTest {

    private final Gson gson = new Gson();

    @Mock
    private LinkTrackerBot bot;
    @InjectMocks
    private TelegramMessageSender messageSenderImpl;

    @Test
    @DisplayName("MessageSender#sendMessage")
    public void sendMessage_shouldCallExecuteMethod_whenMessageHasSent() {
        SendMessage testMessage = new SendMessage(1, "test");
        Mockito.when(bot.execute(testMessage)).thenReturn(getResponse());

        messageSenderImpl.sendMessage(testMessage);

        Mockito.verify(bot).execute(testMessage);
    }

    private SendResponse getResponse() {
        return gson.fromJson("""
                {
                    "responseSend" : {},
                    "responseLogValue" : {},
                    "request" : {},
                    "onCompleteCallback" : {}
                }
            """, SendResponse.class);
    }
}
