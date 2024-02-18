package edu.java.bot.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.commands.CommandService;
import edu.java.bot.configuration.ApplicationConfig;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BotServiceTest {

    @Mock
    private ApplicationConfig applicationConfig;

    @Mock
    private CommandService commandService;

    private BotService botService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        botService = new BotService(applicationConfig, commandService);
    }

    @Test
    void testHandleUpdate() throws Exception {
        // Создание мока Update
        Update mockUpdate = mock(Update.class);

        // Использование рефлексии для вызова приватного метода handleUpdate
        Method handleUpdateMethod = BotService.class.getDeclaredMethod("handleUpdate", Update.class);
        handleUpdateMethod.setAccessible(true); // Делаем метод доступным
        handleUpdateMethod.invoke(botService, mockUpdate); // Вызываем метод с моком Update

        // Проверка взаимодействия с CommandService
        verify(commandService, times(1)).processUpdate(eq(mockUpdate), any());
    }
}
