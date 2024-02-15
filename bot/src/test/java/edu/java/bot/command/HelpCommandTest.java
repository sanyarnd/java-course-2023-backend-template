package edu.java.bot.command;

import edu.java.bot.website.WebsiteInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HelpCommandTest extends CommandTest {
    private HelpCommand helpCommand;

    private Command testCommand;

    @Override
    public void setUp() {
        super.setUp();
        testCommand = mock(ListCommand.class);
        lenient().when(testCommand.type()).thenReturn(CommandInfo.LIST.getType());
        lenient().when(testCommand.description()).thenReturn(CommandInfo.LIST.getDescription());

        helpCommand = new HelpCommand(new ArrayList<>(List.of(testCommand)));
    }

    @Test
    public void testThatReturnedCommandTypeIsCorrect() {
        assertEquals(CommandInfo.HELP.getType(), helpCommand.type());
    }

    @Test
    public void testThatReturnedCommandDescriptionIsCorrect() {
        assertEquals(CommandInfo.HELP.getDescription(), helpCommand.description());
    }

    @Test
    public void testThatMessageWithCommandListIsCorrect() {
        StringBuilder botMessage = new StringBuilder();
        botMessage
            .append("Список поддерживаемых команд:").append("\n")
            .append("/list - Показать список отслеживаемых ссылок.").append("\n\n")
            .append("Ниже приведены поддерживаемые сервисы:");
        for (var website: WebsiteInfo.values()) {
            botMessage.append("\n").append(website.getDomain());
        }
        assertEquals(botMessage.toString(), helpCommand.processCommand(update).getParameters().get("text"));
    }
}
