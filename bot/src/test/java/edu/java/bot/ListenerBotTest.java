package edu.java.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.utility.BotUtils;
import edu.java.bot.command.Command;
import edu.java.bot.command.HelpCommand;
import edu.java.bot.command.ListCommand;
import edu.java.bot.command.StartCommand;
import edu.java.bot.command.UntrackCommand;
import edu.java.bot.message.MessageProcessor;
import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ListenerBotTest {
    @Test
    public void unknownCommandTest() {
        MessageProcessor messageProcessor = new MessageProcessor(List.of(new StartCommand()));
        ListenerBot listenerBot = new ListenerBot(null, messageProcessor);
        String testCommand = "/someCommand";

        Update update = BotUtils.parseUpdate("{\"message\":{\"text\":\"" + testCommand + "\",\"chat\":{\"id\":0}}}");

        SendMessage message = listenerBot.process(update);

        String text = message.getParameters().get("text").toString();
        assertEquals("There is no such command as " + testCommand, text);
    }

    @Test
    public void listCommandTest() {
        MessageProcessor messageProcessor = new MessageProcessor(List.of(new ListCommand()));
        ListenerBot listenerBot = new ListenerBot(null, messageProcessor);
        String testCommand = "/list";

        Update update = BotUtils.parseUpdate("{\"message\":{\"text\":\"" + testCommand + "\",\"chat\":{\"id\":0}}}");

        SendMessage message = listenerBot.process(update);
        assertEquals("There is no links yet :(", message.getParameters().get("text").toString());
    }

    @Test
    public void helpCommandTest() {
        List<Command> commands =
            List.of(
                new StartCommand(),
                new UntrackCommand(),
                new HelpCommand(List.of(
                    new StartCommand(),
                    new UntrackCommand()
                ))
            );

        MessageProcessor mp = new MessageProcessor(commands);
        ListenerBot bot = new ListenerBot(null, mp);

        String testCommand = "/help";

        String expectedResult = "/start : starts interacting with the bot\n" +
            "/untrack : stops tracking link\n" +
            "/help : shows available commands";

        Update update = BotUtils.parseUpdate("{\"message\":{\"text\":\"" + testCommand + "\",\"chat\":{\"id\":0}}}");

        SendMessage message = bot.process(update);

        String text = message.getParameters().get("text").toString();
        assertEquals(expectedResult, text);
    }
}
