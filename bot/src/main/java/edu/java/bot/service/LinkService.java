package edu.java.bot.service;


import edu.java.bot.dto.Link;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class LinkService {

    public List<Link> getAllTrackedLinks(long chatId) {
        //TODO
        return Collections.emptyList();
    }

    public void trackLink(long chatId, String link) {
        // TODO
    }

    public void untrackLink(long chatId, UUID linkId) {
        // TODO
    }
}
