package edu.java.bot.data;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import org.springframework.stereotype.Component;

@Component
public class UsersTracks {
    private HashMap<Long, HashSet<String>> idToURLsMap;

    public UsersTracks() {
        idToURLsMap = new HashMap<>();
    }

    /**
     * Method adds user to the system
     *
     * @return if the user is already registered, then return false, otherwise return true
     */
    public boolean addUser(Long chatId) {
        if (idToURLsMap.containsKey(chatId)) {
            return false;
        }
        idToURLsMap.put(chatId, new HashSet<>());
        return true;
    }

    public boolean containsUser(Long chatId) {
        return idToURLsMap.containsKey(chatId);
    }

    public HashSet<String> getTrackedURLs(Long chatId) {
        return idToURLsMap.get(chatId);
    }

    public boolean addTrackedURLs(Long chatId, String url) {
        if (!checkURL(url)) {
            return false;
        }
        idToURLsMap.get(chatId).add(url);
        addTracking(url);
        return true;
    }

    public boolean removeTrackedURLs(Long chatId, String url) {
        if (!checkURL(url)) {
            return false;
        }
        idToURLsMap.get(chatId).remove(url);
        removeTracking(url);
        return true;
    }

    private boolean checkURL(String url) {
        try {
            new URL(url);
            //TODO: check is allowed URL(github, stackoverflow, etc)
        } catch (MalformedURLException e) {
            return false;
        }
        return true;
    }

    private void addTracking(String url) {
        //TODO: in next iterations
        return;
    }

    private void removeTracking(String url) {
        //TODO: in next iterations
        return;
    }
}
