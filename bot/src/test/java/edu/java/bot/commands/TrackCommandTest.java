package edu.java.bot.commands;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrackCommandTest extends CommandTest {
    @Override
    public void init() {
        super.init();
        command = new TrackCommand(linksRepository);
        command.states = chatStateRepository;
    }

    @SneakyThrows @Test
    public void success() {
        when(linksRepository.addLink(chatId, link)).thenReturn(true);
        assertThat(command.handleNext(chatId, link)).isEqualTo("Done");
    }

    @SneakyThrows @Test
    public void alreadyAdded() {
        when(linksRepository.addLink(chatId, link)).thenReturn(false);
        assertThat(command.handleNext(chatId, link)).isEqualTo("Link has already added");
    }

    @SneakyThrows @Test
    public void incorrectLink() {
        assertThat(command.handleNext(chatId, "...")).isEqualTo("Incorrect URL");
    }

    @AfterEach
    public void checkStatesCleared() {
        verify(chatStateRepository, times(1)).clearState(chatId);
    }
}
