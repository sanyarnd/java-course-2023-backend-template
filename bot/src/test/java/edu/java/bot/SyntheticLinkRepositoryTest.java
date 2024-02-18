package edu.java.bot;

import edu.java.bot.repository.SyntheticLinkRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SyntheticLinkRepositoryTest {

    private static final Long CHAT_ID = 123L;
    private static final String LINK_1 = "https://example.com/link1";
    private static final String LINK_2 = "https://example.com/link2";

    @Test
    void testAdd_AddsLinkToRepository() {
        SyntheticLinkRepository.add(CHAT_ID, LINK_1);

        List<String> links = SyntheticLinkRepository.getAllByChatId(CHAT_ID);

        Assertions.assertEquals(1, links.size());
        Assertions.assertEquals(LINK_1, links.get(0));
        SyntheticLinkRepository.delete(CHAT_ID, 0);
    }

    @Test
    void testAdd_AddsMultipleLinksToRepository() {
        SyntheticLinkRepository.add(CHAT_ID, LINK_1);
        SyntheticLinkRepository.add(CHAT_ID, LINK_2);

        List<String> links = SyntheticLinkRepository.getAllByChatId(CHAT_ID);

        Assertions.assertEquals(2, links.size());
        Assertions.assertEquals(LINK_1, links.get(0));
        Assertions.assertEquals(LINK_2, links.get(1));
        SyntheticLinkRepository.delete(CHAT_ID, 0);
        SyntheticLinkRepository.delete(CHAT_ID, 0);

    }

    @Test
    void testDelete_RemovesLinkFromRepository() {
        SyntheticLinkRepository.add(CHAT_ID, LINK_1);
        SyntheticLinkRepository.add(CHAT_ID, LINK_2);

        SyntheticLinkRepository.delete(CHAT_ID, 0);

        List<String> links = SyntheticLinkRepository.getAllByChatId(CHAT_ID);

        Assertions.assertEquals(1, links.size());
        Assertions.assertEquals(LINK_2, links.get(0));
        SyntheticLinkRepository.delete(CHAT_ID, 0);

    }

}
