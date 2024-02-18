package edu.java.bot.service;

import edu.java.bot.dto.Link;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CommandService {

    // Временное хранилище ссылок в оперативной памяти
    private final Map<Long, List<Link>> trackedLinksMap = new HashMap<>();

    public List<Link> getAllTrackedLinks(long chatId) {
        return trackedLinksMap.getOrDefault(chatId, new ArrayList<>());
    }

    public void trackLink(long chatId, String url) {
        List<Link> links = trackedLinksMap.getOrDefault(chatId, new ArrayList<>());
        links.add(new Link(UUID.randomUUID(), url));
        trackedLinksMap.put(chatId, links);
    }

    public void untrackLink(long chatId, UUID linkId) {
        List<Link> links = trackedLinksMap.get(chatId);
        if (links != null) {
            links.removeIf(link -> link.linkId().equals(linkId));
        }
    }
}
