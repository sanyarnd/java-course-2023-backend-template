package edu.java.bot.repository;

import java.util.HashMap;
import java.util.Map;

public class SyntheticRecentlyUsedRepository {

    private static final Map<Long, String> RECENTLY_USED_MAP = new HashMap<>();

    private SyntheticRecentlyUsedRepository() {
    }

    public static void set(Long chatId, String command) {
        RECENTLY_USED_MAP.put(chatId, command);
    }

    public static String getByChatId(Long chatId) {
        return RECENTLY_USED_MAP.get(chatId);
    }
}
