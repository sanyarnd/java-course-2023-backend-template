package edu.java.bot.repository;

import edu.java.bot.model.UserChat;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserChatRepository {
    private final Map<Long, UserChat> userChats = new HashMap<>();

    public void register(UserChat userChat) {
        userChats.put(userChat.getChatId(), userChat);
    }

    public UserChat findChat(Long chatId) {
        return userChats.get(chatId);
    }

    public List<String> getUserLinks(Long chatId) {
        UserChat userChat = findChat(chatId);
        if (userChat != null) {
            return userChat.getTrackingLinks();
        }
        return null;
    }

    public boolean containsLink(Long chatId, String url) {
        return findChat(chatId).getTrackingLinks().contains(url);
    }

    public void addLink(Long chatId, String link) {
        UserChat userChat = findChat(chatId);
        userChat.getTrackingLinks().add(link);
    }

    public void removeLink(Long chatId, String link) {
        UserChat userChat = findChat(chatId);
        userChat.getTrackingLinks().remove(link);
    }
}
