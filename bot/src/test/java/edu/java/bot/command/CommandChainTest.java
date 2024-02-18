package edu.java.bot.command;

import com.pengrad.telegrambot.request.SendMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static edu.java.bot.util.MessagesUtils.ONLY_TEXT_TO_SEND;

@ExtendWith(MockitoExtension.class)
public class CommandChainTest {

    @Mock
    private CommandExecutor commandExecutor;
    @InjectMocks
    private CommandChain commandChain;

    @Test
    @DisplayName("CommandChain#execute with non text message test")
    public void executeCommand_shouldReturnCorrectMessage_whenRecieveNonTextMessage() {
        SendMessage actual = commandChain.executeCommand(null, 1);

        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(ONLY_TEXT_TO_SEND);
    }

    @Test
    @DisplayName("CommandChain#execute with  text message test")
    public void executeCommand_shouldCallExecuteMethod_whenRecieveTextMessage() {
        String command = "test";
        long chatId = 1;

        commandChain.executeCommand(command, chatId);

        Mockito.verify(commandExecutor, Mockito.only()).execute(command, chatId);
    }

}
