package edu.java.bot.scrappers;

import java.util.ArrayList;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class ScrapperHttpImpl implements Scrapper {
    @Override
    public ArrayList<String> getResourceList(long user_id) {
        return null;
    }

    @Override
    public void track(Long user_id, String resource) {

    }

    @Override
    public void untrack(Long user_id, String resource) {

    }
}
