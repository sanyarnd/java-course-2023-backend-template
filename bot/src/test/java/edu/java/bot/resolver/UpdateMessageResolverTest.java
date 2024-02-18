package edu.java.bot.resolver;


import com.google.gson.Gson;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.command.CommandChain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UpdateMessageResolverTest {

    private final Gson gson = new Gson();

    @Mock
    private CommandChain commandChain;
    @InjectMocks
    private UpdateMessageResolver messageUpdateResolver;

    @Test
    @DisplayName("MessageUpdateResolver#resolve test")
    public void resolve_shouldCallExecuteCommand_whenRecieveMessageUpdate() {
        long chatId = 1;
        String text = "test";

        messageUpdateResolver.resolve(getUpdate(chatId, text));

        Mockito.verify(commandChain, Mockito.only()).executeCommand(text, chatId);
    }

    private Update getUpdate(long chatId, String text) {
        return gson.fromJson("""
            {
                "message": {
                    "text" : %s,
                    "chat" : {
                        "id" : %d
                    }
                }
            }
            """.formatted(text, chatId), Update.class
        );
    }
}
