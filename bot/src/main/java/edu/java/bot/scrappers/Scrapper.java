package edu.java.bot.scrappers;

import java.util.ArrayList;

public interface Scrapper {
    ArrayList<String> getResourceList(long userId);

    void track(Long userId, String resource);

    void untrack(Long userId, String resource);
}
