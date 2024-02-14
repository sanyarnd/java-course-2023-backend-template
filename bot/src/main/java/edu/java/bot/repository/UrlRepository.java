package edu.java.bot.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public class UrlRepository {
    private static UrlRepository instance;
    private final Map<Long, Set<String>> trackedUrls = new HashMap<>(); // Пока в памяти, просто для примера

    public static synchronized UrlRepository getInstance() {
        if (instance == null) {
            instance = new UrlRepository();
        }
        return instance;
    }

    public boolean addUrl(Long chatId, String url) {
        return trackedUrls.computeIfAbsent(chatId, k -> new HashSet<>()).add(url);
    }

    public boolean removeUrl(Long userId, String url) {
        if (trackedUrls.containsKey(userId)) {
            return trackedUrls.get(userId).remove(url);
        }
        return false;
    }

    public Set<String> getUrls(Long userId) {
        return trackedUrls.getOrDefault(userId, Collections.emptySet());
    }
}
