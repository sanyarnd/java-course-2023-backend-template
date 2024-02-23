package edu.java.bot;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import java.util.List;
import edu.java.bot.commands.HelpCommand;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.commands.UntrackCommand;
import edu.java.bot.configuration.ApplicationConfig;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class BotTest {
    List<Command> commands = List.of(new StartCommand(),
        new TrackCommand(),
        //new HelpCommand(),
        new ListCommand(),
        new UntrackCommand()
    );
    private CommandRecognizer recognizer = new CommandRecognizer(commands);
    private LinkValidator validator = new LinkValidator();
    private UserMessageListener listener = new UserMessageListener(recognizer, validator);
    private Bot bot = new Bot(Mockito.mock(ApplicationConfig.class), listener);


    //    private final UserMessageListener listener = new UserMessageListener(
//        Mockito.mock(CommandRecognizer.class),
//        Mockito.mock(LinkValidator.class),
//        Mockito.mock(ApplicationContext.class)
//    );
//    private final Bot bot = new Bot(Mockito.mock(ApplicationConfig.class), listener);
    private Update getMockUpdate(String text) {
        var mock = Mockito.mock(Update.class);
        Mockito.when(mock.message()).thenReturn(Mockito.mock(Message.class));
        Mockito.when(mock.message().chat()).thenReturn(Mockito.mock(Chat.class));
        Mockito.when(mock.message().chat().id()).thenReturn(1L);
        Mockito.when(mock.message().text()).thenReturn(text);
        return mock;
    }

    @Test
    void recognize_shouldRecognizeCommand() {
        //arrange
        Update mock = getMockUpdate("/start");
        var exp = StartCommand.class;
        //act
        var act = recognizer.recognize(mock);
        //assert
        assertThat(act).isEqualTo(exp);
    }

    @Test
    void process_shouldReturnConfirmedUpdatesAll() {
        //arrange
        Update mock = getMockUpdate("/help");
        //act
        var act = bot.process(List.of(mock));
        //assert
        assertThat(act).isEqualTo(UpdatesListener.CONFIRMED_UPDATES_ALL);
    }

    @Test
    void recognizeCommand_shouldRecognizeStartCommand() {
        //arrange
        Update mock = getMockUpdate("/start");
        String exp = "You are registered for resource tracking";
        //act
        var act = listener.recognizeCommand(mock).getParameters().get("text");
        //assert
        assertThat(act).isEqualTo(exp);
    }

    @Test
    void recognizeCommand_shouldRecognizeTrackCommand() {
        //arrange
        Update mock = getMockUpdate("/track");
        String exp = "Input link for tracking:";
        //act
        var act = listener.recognizeCommand(mock);
        //assert
        assertThat(act).isEqualTo(exp);
    }

    @Test
    void recognizeCommand_shouldRecognizeUnknownCommand() {
        //arrange
        Update mock = getMockUpdate("/hello");
        String exp = "Unknown command. Use /help to see the available commands";
        //act
        var act = listener.recognizeCommand(mock);
        //assert
        assertThat(act).isEqualTo(exp);
    }

    @Test
    void linkValidationInDialog_shouldMatchCorrectLinks() {
        //arrange
        Update mock = getMockUpdate("https://stackoverflow.com/questions/1");
        String exp = "Link successfully added for tracking!";
        //act
        var act = listener.linkValidationInDialog(mock).getParameters().get("text");
        //assert
        assertThat(act).isEqualTo(exp);
    }

    @Test
    void linkValidationInDialog_shouldMatchIncorrectLinks() {
        //arrange
        Update mock = getMockUpdate("https://stackoverflow.com/questions/");
        String exp = "Incorrect input";
        //act
        var act = listener.linkValidationInDialog(mock).getParameters().get("text");
        //assert
        assertThat(act).isEqualTo(exp);
    }
}
