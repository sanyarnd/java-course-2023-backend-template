package edu.java.bot.commands;

import edu.java.bot.exceptions.UserIsNotRegisteredException;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ListCommandTest extends CommandTest {
    @Override
    public void init() {
        super.init();
        command = new ListCommand(linksRepository);
        command.states = chatStateRepository;
    }

    @SneakyThrows @Test
    public void notRegistered() {
        when(linksRepository.getLinks(chatId)).thenThrow(new UserIsNotRegisteredException());
        assertThatThrownBy(() -> command.run(chatId))
            .isInstanceOf(UserIsNotRegisteredException.class);
    }

    @SneakyThrows @Test
    public void noLinks() {
        when(linksRepository.getLinks(chatId)).thenReturn(List.of());
        assertThat(command.run(chatId)).isEqualTo("No tracked links");
    }

    @SneakyThrows @ParameterizedTest
    @MethodSource("linksFactory")
    public void hasLinks(List<String> list) {
        when(linksRepository.getLinks(chatId)).thenReturn(list);
        assertThat(command.run(chatId))
            .isEqualTo("List of tracked links: \n" + String.join("\n", list));
    }

    static @NotNull Stream<List<String>> linksFactory() {
        return Stream.of(
            List.of("link1, link2"),
            List.of("link1, link2", "link3, link4"),
            List.of("https://github.com/0SNP0/java-course-2023-backend-0SNP0")
        );
    }
}
