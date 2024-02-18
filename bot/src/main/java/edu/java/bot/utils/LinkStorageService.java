package edu.java.bot.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class LinkStorageService {
    private final Map<Long, Set<String>> userLinks = new HashMap<>();

    public void addLink(Long userId, String link) {
        userLinks.computeIfAbsent(userId, k -> new HashSet<>()).add(link);
    }

    public Set<String> getLinks(Long userId) {
        return userLinks.getOrDefault(userId, new HashSet<>());
    }

    public boolean removeLink(Long userId, String link) {
        Set<String> links = userLinks.getOrDefault(userId, new HashSet<>());
        return links.remove(link);
    }

    public void removeAllLinks(Long userId) {
        userLinks.remove(userId);
    }
}
