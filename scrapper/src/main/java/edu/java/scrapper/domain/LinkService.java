package edu.java.scrapper.domain;

import edu.java.scrapper.data.db.entity.Link;
import java.net.URL;
import java.util.List;

public interface LinkService {
    Link add(Long telegramChatId, URL url);

    Link remove(Long telegramChatId, URL url);

    List<Link> getAllForChat(Long chatId);
}
