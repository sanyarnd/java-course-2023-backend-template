package edu.java.bot.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.comand.Command;
import edu.java.bot.comand.HelpCommand;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class HelpCommandTest {

    private final List<Command> commandList = new ArrayList<>();
    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private Chat chat;
    @InjectMocks
    private HelpCommand helpCommand;

    @BeforeEach
    public void setUp() {
        helpCommand = new HelpCommand(commandList);
        given(update.message()).willReturn(message);
        given(message.chat()).willReturn(chat);
        given(chat.id()).willReturn(123L);
    }

    @Test
    void testHelpCommandWithCommands() {
        Command command1 = mock(Command.class);
        given(command1.command()).willReturn("/start");
        given(command1.description()).willReturn("Start the bot and get welcome message");

        Command command2 = mock(Command.class);
        given(command2.command()).willReturn("/help");
        given(command2.description()).willReturn("displays a list of available commands");

        commandList.addAll(Arrays.asList(command1, command2));

        SendMessage result = helpCommand.handle(update);

        String text = (String) result.getParameters().get("text");
        assertThat(text).isEqualTo("""
            /start - Start the bot and get welcome message
            /help - displays a list of available commands
            """);
    }
}
