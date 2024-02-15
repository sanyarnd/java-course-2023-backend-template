package edu.java.bot.command;

import edu.java.bot.model.UserChat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class StartCommandTest extends CommandTest {
    @InjectMocks
    private StartCommand startCommand;

    @Test
    @Override
    void testThatReturnedCommandTypeIsCorrect() {
        assertEquals(CommandInfo.START.getType(), startCommand.type());
    }

    @Test
    @Override
    void testThatReturnedCommandDescriptionIsCorrect() {
        assertEquals(CommandInfo.START.getDescription(), startCommand.description());
    }

    @Test
    void testThatNewUserAddedToRepository() {
        startCommand.processCommand(update);

        UserChat userChat = userChatRepository.findChat(chatId);
        assertNotNull(userChat);
    }

    @Test
    void testThatCommandReturnCorrectMessageForNewUser() {
        String message = "Приветствую! Вы зарегистрировались в приложении Link Tracker!\n"
            + "Чтобы вывести список доступных команд, используйте /help";
        assertEquals(message, startCommand.processCommand(update).getParameters().get("text"));
    }

    @Test
    void testThatCommandReturnCorrectMessageForRegisteredUser() {
        startCommand.processCommand(update);

        String message = "Вы уже зарегистрированы :)\n"
            + "Чтобы вывести список доступных команд, используйте /help";
        String botMessage = startCommand.processCommand(update).getParameters().get("text").toString();

        assertEquals(message, botMessage);
    }
}
