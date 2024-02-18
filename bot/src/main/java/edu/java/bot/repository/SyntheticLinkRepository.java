package edu.java.bot.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyntheticLinkRepository {

    private SyntheticLinkRepository() {
    }

    private static final Map<Long, List<String>> LINK_REPOSITORY = new HashMap<>();

    public static void add(Long chatId, String link) {
        if (LINK_REPOSITORY.containsKey(chatId)) {
            LINK_REPOSITORY.get(chatId).add(link);
        } else {
            List<String> newList = new ArrayList<>();
            newList.add(link);
            LINK_REPOSITORY.put(chatId, newList);
        }
    }

    public static void delete(Long chatId, int linkIndex) {
        LINK_REPOSITORY.get(chatId).remove(linkIndex);
    }

    public static List<String> getAllByChatId(Long chatId) {
        return LINK_REPOSITORY.get(chatId);
    }

}
