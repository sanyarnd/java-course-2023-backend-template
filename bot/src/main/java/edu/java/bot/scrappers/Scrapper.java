package edu.java.bot.scrappers;

import java.util.ArrayList;

public interface Scrapper {
    ArrayList<String> getResourceList(long user_id);

    void track(Long user_id, String resource);

    void untrack(Long user_id, String resource);
}
