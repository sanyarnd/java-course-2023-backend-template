package edu.java.bot.repositories;

import edu.java.bot.exceptions.UserIsNotRegisteredException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.stereotype.Repository;

@Repository
public class LinksMemoryRepository implements LinksRepository {
    private final ConcurrentMap<Long, Set<String>> map = new ConcurrentHashMap<>();

    @Override
    public boolean register(long chatId) {
        if (map.containsKey(chatId)) {
            return false;
        }
        map.put(chatId, new HashSet<>());
        return true;
    }

    private Set<String> getSet(long chatId) throws UserIsNotRegisteredException {
        var set = map.get(chatId);
        if (set == null) {
            throw new UserIsNotRegisteredException();
        }
        return set;
    }

    @Override
    public boolean addLink(long chatId, String link) throws UserIsNotRegisteredException {
        return getSet(chatId).add(link);
    }

    @Override
    public List<String> getLinks(long chatId) throws UserIsNotRegisteredException {
        return getSet(chatId).stream().toList();
    }

    @Override
    public boolean removeLink(long chatId, String link) throws UserIsNotRegisteredException {
        return getSet(chatId).remove(link);
    }
}
