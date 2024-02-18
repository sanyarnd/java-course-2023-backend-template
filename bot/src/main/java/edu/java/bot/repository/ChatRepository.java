package edu.java.bot.repository;

import edu.java.bot.BotState;
import edu.java.bot.repository.Dao.Chat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import org.springframework.stereotype.Repository;


@Repository
public class ChatRepository {
    HashMap<Long, Chat> chats;

    public ChatRepository() {
        chats = new HashMap<Long, Chat>();
    }

    public Optional<Chat> findById(Long id) {
        return Optional.ofNullable(chats.get(id));
    }

    public boolean insertById(Long id) {
        return chats.put(id, new Chat(BotState.READY, id, new ArrayList<>())) != null;
    }

    public boolean insertByChat(Chat chat) {
        return chats.put(chat.getChatId(), chat) != null;
    }
}
