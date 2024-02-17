package edu.java.bot.commands;

import edu.java.bot.repositories.ChatStateRepository;
import edu.java.bot.repositories.LinksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

public abstract class CommandTest {
    protected ChatStateRepository chatStateRepository;
    protected LinksRepository linksRepository;
    protected AbstractCommand command;
    protected final long chatId = 1L;
    protected final String link = "https://github.com/0SNP0/java-course-2023-backend-0SNP0";

    @BeforeEach
    public void init() {
        chatStateRepository = Mockito.spy(ChatStateRepository.class);
        linksRepository = Mockito.spy(LinksRepository.class);
    }
}
