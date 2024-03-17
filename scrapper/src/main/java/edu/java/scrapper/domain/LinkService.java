package edu.java.scrapper.domain;

import edu.java.scrapper.data.db.entity.Link;
import java.util.List;

public interface LinkService {
    Link add(Long telegramChatId, String url);

    Link remove(Long telegramChatId, String url);

    List<Link> getAllForChat(Long chatId);
}
