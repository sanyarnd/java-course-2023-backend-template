package edu.java.bot.command;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class ListCommandTest extends CommandTest {
    @InjectMocks
    private ListCommand listCommand;

    @Test
    @Override
    public void testThatReturnedCommandTypeIsCorrect() {
        assertEquals(CommandInfo.LIST.getType(), listCommand.type());
    }

    @Test
    @Override
    public void testThatReturnedCommandDescriptionIsCorrect() {
        assertEquals(CommandInfo.LIST.getDescription(), listCommand.description());
    }

    @Test
    public void testThatReturnedListOfLinksIsCorrect() {
        doReturn(List.of(
            "https://github.com/sanyarnd/java-course-2023-backend-template",
            "https://github.com/sanyarnd/java-course-2023",
            "https://stackoverflow.com/questions/66696828/how-to-use-configurationproperties-with-records"
        )).when(userChatRepository).getUserLinks(chatId);

        StringBuilder message = new StringBuilder();
        message.append("Список отслеживаемых ссылок:");
        for (String link : userChatRepository.getUserLinks(chatId)) {
            message.append("\n").append(link);
        }

        assertEquals(message.toString(), listCommand.processCommand(update).getParameters().get("text"));
    }

    @Test
    public void testReturnedMessageWhenListOfLinksIsEmpty() {
        doReturn(Collections.emptyList()).when(userChatRepository).getUserLinks(chatId);

        String message = "Список отслеживаемых ссылок пуст. Для добавления ссылки используйте /track";
        assertEquals(message, listCommand.processCommand(update).getParameters().get("text"));
    }
}
