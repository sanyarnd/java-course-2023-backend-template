package edu.java.bot.repository;

import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.domain.Chat;
import edu.java.bot.exception.DBException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ChatRepository extends BaseRepository {
    public ChatRepository(ApplicationConfig applicationConfig) {
        super(applicationConfig);
    }

    public void save(Chat chat) {
        try {
            List<Chat> chats = readDatabase();
            chats.add(chat);
            writeDatabase(chats);
        } catch (IOException e) {
            throw new DBException(ERROR_MESSAGE, e);
        }
    }

    public Optional<Chat> findById(long id) {
        try {
            List<Chat> chats = readDatabase();
            return chats.stream().filter(chat -> chat.getId() == id).findFirst();
        } catch (IOException e) {
            throw new DBException(ERROR_MESSAGE, e);
        }
    }
}
