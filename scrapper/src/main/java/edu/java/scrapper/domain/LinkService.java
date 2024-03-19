package edu.java.scrapper.domain;

import edu.java.core.exception.LinkIsUnreachable;
import edu.java.core.exception.UserNotRegistered;
import edu.java.core.exception.link.LinkAlreadyRegistered;
import edu.java.core.exception.link.LinkNotRegistered;
import edu.java.scrapper.data.db.entity.Link;
import java.util.List;

public interface LinkService {
    Link add(Long telegramChatId, String url) throws LinkIsUnreachable, LinkAlreadyRegistered, UserNotRegistered;

    Link remove(Long telegramChatId, String url) throws LinkNotRegistered, UserNotRegistered;

    List<Link> getAllForChat(Long chatId);
}
