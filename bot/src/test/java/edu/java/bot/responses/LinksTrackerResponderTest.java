package edu.java.bot.responses;

import edu.java.bot.commands.AbstractCommand;
import edu.java.bot.commands.CommandWithArgs;
import edu.java.bot.commands.CommandWithoutArgs;
import edu.java.bot.commands.Commands;
import edu.java.bot.exceptions.UserIsNotRegisteredException;
import edu.java.bot.repositories.ChatStateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LinksTrackerResponderTest {
    private final long chatId = 1L;
    private ChatStateRepository chatStateRepository;
    private LinksTrackerResponder responder;

    private AbstractCommand commandNoArg;
    private AbstractCommand commandWithArg;
    private AbstractCommand commandThrows;

    @BeforeEach
    public void init() {
        chatStateRepository = spy(ChatStateRepository.class);
        commandNoArg = new CommandWithoutArgs(
            "/commandNoArg",
            "Command without arguments",
            ANSWER
        ) {
            @Override
            public String run(long chatId) {
                chatStateRepository.clearState(chatId);
                return answer;
            }
        };
        commandWithArg = new CommandWithArgs(
            "/commandWithArg",
            "Command with arguments",
            ANSWER
        ) {
            @Override
            public String run(long chatId) {
                chatStateRepository.setState(chatId, this);
                return answer;
            }

            @Override
            public String handleNext(long chatId, String message) {
                chatStateRepository.clearState(chatId);
                return SUCCESS;
            }
        };
        commandThrows = new CommandWithoutArgs(
            "/commandThrows",
            "Command requires to register",
            ANSWER
        ) {
            @Override
            public String run(long chatId) throws UserIsNotRegisteredException {
                throw new UserIsNotRegisteredException();
            }
        };

        responder = new LinksTrackerResponder(
            new Commands(List.of(commandNoArg, commandWithArg, commandThrows)),
            chatStateRepository
        );
    }

    @Test
    public void simpleCommand() {
        assertThat(responder.getAnswer(chatId, commandNoArg.command())).isEqualTo(ANSWER);
        verify(chatStateRepository, times(1)).clearState(chatId);
    }

    @Test
    public void excessArgs() {
        assertThat(responder.getAnswer(chatId, commandNoArg.command() + " qwerty"))
            .isEqualTo("This command doesn't need arguments.");
    }

    @Test
    public void unknownCommand() {
        assertThat(responder.getAnswer(chatId, "/unknown"))
            .isEqualTo("Unknown command. Type /help for commands list.");
    }

    @Test
    public void unknownCommandWithArgs() {
        assertThat(responder.getAnswer(chatId, "/unknown a b c"))
            .isEqualTo("Unknown command. Type /help for commands list.");
    }

    @Test
    public void argsInSameMessage() {
        assertThat(responder.getAnswer(chatId, commandWithArg.command() + " l://i.n.k"))
            .isEqualTo(SUCCESS);
        verify(chatStateRepository, times(1)).clearState(chatId);
    }

    @Test
    public void argsInSeparatedMessage() {
        assertThat(responder.getAnswer(chatId, commandWithArg.command())).isEqualTo(ANSWER);
        verify(chatStateRepository, times(1)).setState(chatId, commandWithArg);
        when(chatStateRepository.getCurrentCommand(chatId)).thenReturn(commandWithArg);
        assertThat(responder.getAnswer(chatId, "l://i.n.k")).isEqualTo(SUCCESS);
        verify(chatStateRepository, times(1)).clearState(chatId);
    }

    @Test
    public void noCommand() {
        when(chatStateRepository.getCurrentCommand(chatId))
            .thenReturn(ChatStateRepository.NoCommand.defaultState);
        assertThat(responder.getAnswer(chatId, "no command"))
            .isEqualTo("No selected command");
    }

    @Test
    public void needToRegister() {
        assertThat(responder.getAnswer(chatId, commandThrows.command()))
            .isEqualTo("You should to register at first.");
        verify(chatStateRepository, times(1)).clearState(chatId);
    }

    private static final String SUCCESS = "Success";
    private static final String ANSWER = "Answer";
}
