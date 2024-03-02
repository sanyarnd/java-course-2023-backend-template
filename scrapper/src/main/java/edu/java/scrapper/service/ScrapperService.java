package edu.java.scrapper.service;

import edu.java.scrapper.dto.LinkResponse;
import edu.java.scrapper.dto.ListLinksResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ScrapperService {
    private final Map<Long, ListLinksResponse> chatsWithLinks = new HashMap<>();

    public void registerChat(long chatId) {
        chatsWithLinks.put(chatId, new ListLinksResponse(0, new ArrayList<>()));
    }

    public void deleteChat(Long chatId) {
        chatsWithLinks.remove(chatId);
    }

    public ListLinksResponse getLinks(Long chatId) {
        return chatsWithLinks.get(chatId);
    }

    public LinkResponse addLink(Long chatId, LinkResponse link) {
        ListLinksResponse linkResponses = chatsWithLinks.get(chatId);
        linkResponses.addLinkResponse(link);
        chatsWithLinks.put(chatId, linkResponses);

        return link;
    }

    public LinkResponse deleteLink(Long chatId, LinkResponse link) {
        ListLinksResponse linkResponses = chatsWithLinks.get(chatId);
        linkResponses.deleteLinkResponse(link);
        chatsWithLinks.put(chatId, linkResponses);

        return link;
    }

}
