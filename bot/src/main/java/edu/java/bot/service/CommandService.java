package edu.java.bot.service;


import edu.java.bot.dto.Link;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CommandService {

    public List<Link> getAllTrackedLinks(long chatId) {
        return Collections.emptyList();
    }

    public void trackLink(long chatId, String link) {
    }

    public void untrackLink(long chatId, UUID linkId) {

    }
    public void registration(long userId){

    }
}
