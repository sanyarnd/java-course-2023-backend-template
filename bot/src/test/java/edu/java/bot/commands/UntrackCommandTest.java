package edu.java.bot.commands;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UntrackCommandTest extends CommandTest {
    @Override
    public void init() {
        super.init();
        command = new UntrackCommand(linksRepository);
        command.states = chatStateRepository;
    }

    @SneakyThrows @Test
    public void success() {
        when(linksRepository.removeLink(chatId, link)).thenReturn(true);
        assertThat(command.handleNext(chatId, link)).isEqualTo("Done");
    }

    @SneakyThrows @Test
    public void alreadyAdded() {
        when(linksRepository.removeLink(chatId, link)).thenReturn(false);
        assertThat(command.handleNext(chatId, link)).isEqualTo("Link hasn't added");
    }

    @AfterEach
    public void checkStatesCleared() {
        verify(chatStateRepository, times(1)).clearState(chatId);
    }
}
