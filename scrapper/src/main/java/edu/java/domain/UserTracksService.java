package edu.java.domain;

import edu.java.controller.exception.CantHandleURLException;
import edu.java.controller.exception.ChatNotFoundException;
import edu.java.controller.exception.ChatReAddingException;
import edu.java.controller.exception.LinkNotFoundException;
import edu.java.controller.exception.LinkReAddingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.stereotype.Component;

@Deprecated
@Component
public class UserTracksService {
    private HashMap<Long, ArrayList<URI>> idToURLsMap;

    public UserTracksService() {
        idToURLsMap = new HashMap<>();
    }

    /**
     * Method adds user to the system
     *
     * @return if the user is already registered, then return false, otherwise return true
     */
    public boolean addUser(Long chatId) {
        if (idToURLsMap.containsKey(chatId)) {
            throw new ChatReAddingException("Пользователь со следующим id уже добавлен: " + chatId);
        }
        idToURLsMap.put(chatId, new ArrayList<>());
        return true;
    }

    public boolean removeUser(Long chatId) {
        checkChatInSystem(chatId);
        idToURLsMap.remove(chatId);
        return true;
    }

    public boolean containsUser(Long chatId) {
        return idToURLsMap.containsKey(chatId);
    }

    public ArrayList<URI> getTrackedURLs(Long chatId) {
        checkChatInSystem(chatId);
        return idToURLsMap.get(chatId);
    }

    public Long addTrackedURLs(Long chatId, String providedURL) {
        URI url = null;
        try {
            url = new URI(providedURL);
        } catch (URISyntaxException ex) {
            throwBadURLException(providedURL);
        }

        if (!checkURL(url)) {
            throwBadURLException(url.toString());
        }
        checkChatInSystem(chatId);
        if (idToURLsMap.get(chatId).contains(url)) {
            throw new LinkReAddingException(
                "У чата со следующим id: " + chatId + " уже есть подпись на сайт: " + url.toString());
        }
        idToURLsMap.get(chatId).add(url);
        addTracking(url);
        //TODO::Add return index in next iterations
        return 0L;
    }

    public Long removeTrackedURLs(Long chatId, String providedURL) {
        URI url = null;
        try {
            url = new URI(providedURL);
        } catch (URISyntaxException ex) {
            throwBadURLException(providedURL);
        }
        if (!checkURL(url)) {
            throwBadURLException(url.toString());
        }
        checkChatInSystem(chatId);
        if (!idToURLsMap.get(chatId).contains(url)) {
            throw new LinkNotFoundException("У чата с id: " + chatId + " нет подписи на сайт: " + url.toString());
        }
        idToURLsMap.get(chatId).remove(url);
        removeTracking(url);
        //TODO::Add return index in next iterations
        return 0L;
    }

    private void checkChatInSystem(Long chatId) {
        if (!idToURLsMap.containsKey(chatId)) {
            throw new ChatNotFoundException("Нет чата с id: " + chatId);
        }
    }

    private void throwBadURLException(String url) {
        throw new CantHandleURLException("Сервис не может обработать ссылку: " + url);
    }

    private boolean checkURL(URI url) {
        //TODO: check is allowed URL(github, stackoverflow, etc)
        return true;
    }

    private void addTracking(URI url) {
        //TODO: in next iterations
        return;
    }

    private void removeTracking(URI url) {
        //TODO: in next iterations
        return;
    }
}
