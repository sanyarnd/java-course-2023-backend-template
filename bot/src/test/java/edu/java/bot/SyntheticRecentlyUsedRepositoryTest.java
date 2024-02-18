package edu.java.bot;

import edu.java.bot.repository.SyntheticRecentlyUsedRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SyntheticRecentlyUsedRepositoryTest {

    private static final Long CHAT_ID_1 = 123L;
    private static final Long CHAT_ID_2 = 456L;
    private static final String COMMAND_1 = "command1";
    private static final String COMMAND_2 = "command2";

    @Test
    void testSet_SetsCommandForDifferentChatIds() {
        SyntheticRecentlyUsedRepository.set(CHAT_ID_1, COMMAND_1);
        SyntheticRecentlyUsedRepository.set(CHAT_ID_2, COMMAND_2);

        String command1 = SyntheticRecentlyUsedRepository.getByChatId(CHAT_ID_1);
        String command2 = SyntheticRecentlyUsedRepository.getByChatId(CHAT_ID_2);

        Assertions.assertEquals(COMMAND_1, command1);
        Assertions.assertEquals(COMMAND_2, command2);
    }

}
