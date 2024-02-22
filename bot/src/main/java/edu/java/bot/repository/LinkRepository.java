package edu.java.bot.repository;

import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.domain.Chat;
import edu.java.bot.domain.Link;
import edu.java.bot.exception.DBException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class LinkRepository extends BaseRepository {
    public LinkRepository(ApplicationConfig applicationConfig) {
        super(applicationConfig);
    }

    public void addLink(long chatId, Link link) {
        try {
            List<Chat> chats = readDatabase();
            Chat chat = findChat(chats, chatId);
            chat.addLink(link);
            writeDatabase(chats);
        } catch (IOException e) {
            throw new DBException(ERROR_MESSAGE, e);
        }
    }

    public List<Link> findAll(long chatId) {
        try {
            List<Chat> chats = readDatabase();
            Chat chat = findChat(chats, chatId);
            return chat.getLinks();
        } catch (IOException e) {
            throw new DBException(ERROR_MESSAGE, e);
        }
    }

    public void deleteLink(long chatId, Link link) {
        try {
            List<Chat> chats = readDatabase();
            Chat chat = findChat(chats, chatId);
            chat.getLinks().remove(link);
            writeDatabase(chats);
        } catch (IOException e) {
            throw new DBException("Unable to load database.", e);
        }
    }

    public Optional<Link> find(long chatId, String url) {
        List<Link> links = findAll(chatId);
        return links.stream().filter(link -> link.getUrl().equals(url)).findFirst();
    }
}
