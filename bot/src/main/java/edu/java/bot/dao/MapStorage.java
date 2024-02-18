package edu.java.bot.dao;

import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MapStorage {

    private final Map<Long, List<URI>> storage = new HashMap<>();

    private static final String USER_ERROR = "The user isn't logged in";

    public void registrate(Update update) {
        storage.put(update.message().chat().id(), new ArrayList<>());
    }

    public boolean isRegistered(Update update) {
        return storage.containsKey(update.message().chat().id());
    }

    public void addSubscription(Update update, URI link) throws Exception {
        if (isRegistered(update)) {
            List<URI> subs = storage.get(update.message().chat().id());
            subs.add(link);
        } else {
            throw new Exception(USER_ERROR);
        }
    }

    public void deleteSubscription(Update update, URI link) throws Exception {
        if (isRegistered(update)) {
            List<URI> subs = storage.get(update.message().chat().id());
            subs.remove(link);
        } else {
            throw new Exception(USER_ERROR);
        }
    }

    public List<URI> getSubscriptions(Update update) throws Exception {
        if (isRegistered(update)) {
            return storage.get(update.message().chat().id());
        } else {
            throw new Exception(USER_ERROR);
        }
    }

    public boolean isSubExists(Update update, URI link) throws Exception {
        if (isRegistered(update)) {
            return storage.get(update.message().chat().id()).contains(link);
        } else {
            throw new Exception(USER_ERROR);
        }
    }

    public void clear() {
        storage.clear();
    }


}
