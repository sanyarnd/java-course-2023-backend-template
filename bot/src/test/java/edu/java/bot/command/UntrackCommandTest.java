package edu.java.bot.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class UntrackCommandTest extends CommandTest {
    @InjectMocks
    private UntrackCommand untrackCommand;

    private static final String GIT_HUB_LINK = "https://github.com/pengrad/java-telegram-bot-api";

    private static final String STACK_OVERFLOW_LINK =
        "https://stackoverflow.com/questions/28295625/mockito-spy-vs-mock";

    @Test
    @Override
    void testThatReturnedCommandTypeIsCorrect() {
        assertEquals(CommandInfo.UNTRACK.getType(), untrackCommand.type());
    }

    @Test
    @Override
    void testThatReturnedCommandDescriptionIsCorrect() {
        assertEquals(CommandInfo.UNTRACK.getDescription(), untrackCommand.description());
    }

    @Test
    public void testIncorrectCommandFormatMessage() {
        doReturn("/untrack").when(message).text();
        assertEquals("Неверный формат команды. /help",
            untrackCommand.processCommand(update).getParameters().get("text"));
    }

    @Test
    public void testSuccessfulRemoval() {
        doReturn("/untrack " + GIT_HUB_LINK).when(message).text();
        doReturn(true).when(userChatRepository).containsLink(chatId, GIT_HUB_LINK);

        assertEquals("Сылка успешно удалена. /list",
            untrackCommand.processCommand(update).getParameters().get("text"));
    }

    @Test
    public void testLinkNotFoundMessage() {
        String botMessage = "Ничего не найдено :( /list";

        doReturn("/untrack " + STACK_OVERFLOW_LINK).when(message).text();
        doReturn(false).when(userChatRepository).containsLink(chatId, STACK_OVERFLOW_LINK);
        assertEquals(botMessage, untrackCommand.processCommand(update).getParameters().get("text"));
    }
}
