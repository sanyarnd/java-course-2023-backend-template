package edu.java.scrapper.domain;

import edu.java.core.exception.*;
import edu.java.scrapper.data.db.entity.Link;
import java.util.List;

public interface LinkService {
    Link add(Long telegramChatId, String url)
            throws LinkCannotBeHandledException, LinkAlreadyTrackedException, LinkIsNotRegisteredException, UserIsNotAuthorizedException;

    Link remove(Long telegramChatId, String url)
            throws LinkIsNotTrackedException, LinkIsNotRegisteredException, UserIsNotAuthorizedException;

    List<Link> getAllForChat(Long chatId);
}
