package edu.java.scrapper.data.db.impl.jdbc;

import edu.java.core.exception.LinkAlreadyTrackedException;
import edu.java.core.exception.LinkIsNotRegisteredException;
import edu.java.core.exception.LinkIsNotTrackedException;
import edu.java.core.exception.UserIsNotAuthorizedException;
import edu.java.scrapper.data.db.LinkRepository;
import edu.java.scrapper.data.db.TelegramChatRepository;
import edu.java.scrapper.data.db.TrackerRepository;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.TelegramChat;
import java.util.List;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class JdbcTrackerRepositoryImpl implements TrackerRepository {
    private static final String ADD_CHAT_TO_LINK = "INSERT INTO chat_to_link_binding (chat_id, link_id) VALUES (?, ?)";
    private static final String DELETE_CHAT_TO_LINK = "DELETE FROM chat_to_link_binding WHERE chat_id=? AND link_id=?";
    private final static String FIND_CHATS_SUBSCRIBED_TO_LINK_ID = "SELECT telegram_chat.id, "
            + "telegram_chat.registered_at FROM telegram_chat INNER JOIN chat_to_link_binding "
            + "ON telegram_chat.id = chat_to_link_binding.chat_id AND chat_to_link_binding.link_id = ?";
    private final static String FIND_LINKS_SUBSCRIBED_WITH_CHAT_ID = "SELECT link.id, link.url, link.last_updated_at "
            + "FROM link INNER JOIN chat_to_link_binding "
            + "ON link.id = chat_to_link_binding.link_id AND chat_to_link_binding.chat_id=?";
    private final LinkRepository linkRepository;
    private final TelegramChatRepository telegramChatRepository;
    private final JdbcClient client;

    public JdbcTrackerRepositoryImpl(
            LinkRepository linkRepository,
            TelegramChatRepository telegramChatRepository,
            JdbcClient client
    ) {
        this.linkRepository = linkRepository;
        this.telegramChatRepository = telegramChatRepository;
        this.client = client;
    }

    @Override
    public void track(Link link, TelegramChat telegramChat)
            throws LinkAlreadyTrackedException, LinkIsNotRegisteredException, UserIsNotAuthorizedException {
        validateAndProcess(link, telegramChat, ADD_CHAT_TO_LINK);
    }

    @Override
    public void untrack(Link link, TelegramChat telegramChat)
            throws LinkIsNotTrackedException, LinkIsNotRegisteredException, UserIsNotAuthorizedException {
        validateAndProcess(link, telegramChat, DELETE_CHAT_TO_LINK);
    }

    private void validateAndProcess(Link link, TelegramChat telegramChat, String statement) {
        Link validLink = linkRepository.findById(link.getId())
                .orElseThrow(() -> new LinkIsNotRegisteredException(link.getUrl()));
        TelegramChat validTelegramChat = telegramChatRepository.findById(telegramChat.getId())
                .orElseThrow(() -> new UserIsNotAuthorizedException(telegramChat.getId()));
        try {
            int rowsAffected = client.sql(statement)
                    .param(validTelegramChat.getId())
                    .param(validLink.getId())
                    .update();
            if (rowsAffected == 0) {
                throw new LinkIsNotTrackedException(validLink.getUrl());
            }
        } catch (DuplicateKeyException duplicateKeyException) {
            throw new LinkAlreadyTrackedException(validLink.getId(), validTelegramChat.getId());
        }
    }

    @Override
    public List<TelegramChat> findAllChatsSubscribedTo(Link link) {
        return client.sql(FIND_CHATS_SUBSCRIBED_TO_LINK_ID)
                .param(link.getId())
                .query(new BeanPropertyRowMapper<>(TelegramChat.class))
                .list();
    }

    @Override
    public List<Link> findAllLinksSubscribedWith(TelegramChat chat) {
        return client.sql(FIND_LINKS_SUBSCRIBED_WITH_CHAT_ID)
                .param(chat.getId())
                .query(new BeanPropertyRowMapper<>(Link.class))
                .list();
    }
}
