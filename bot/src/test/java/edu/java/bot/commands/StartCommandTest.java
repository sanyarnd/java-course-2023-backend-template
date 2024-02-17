package edu.java.bot.commands;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StartCommandTest extends CommandTest {
    @Override
    public void init() {
        super.init();
        command = new StartCommand(linksRepository);
        command.states = chatStateRepository;
    }

    @SneakyThrows @Test
    public void registration() {
        when(linksRepository.register(chatId)).thenReturn(true);
        assertThat(command.run(chatId)).isEqualTo("You are successfully registered!");
    }

    @SneakyThrows @Test
    public void alreadyRegistered() {
        when(linksRepository.register(chatId)).thenReturn(false);
        assertThat(command.run(chatId)).isEqualTo("You have already registered!");
    }


    @AfterEach
    public void checkStatesCleared() {
        verify(chatStateRepository, times(1)).clearState(chatId);
    }
}
