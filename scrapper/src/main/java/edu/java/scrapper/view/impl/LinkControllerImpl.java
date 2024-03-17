package edu.java.scrapper.view.impl;

import edu.java.core.request.AddLinkRequest;
import edu.java.core.request.RemoveLinkRequest;
import edu.java.core.response.LinkResponse;
import edu.java.core.response.ListLinksResponse;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.domain.LinkService;
import edu.java.scrapper.view.LinkController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class LinkControllerImpl implements LinkController {
    private final LinkService linkService;

    public LinkControllerImpl(LinkService linkService) {
        this.linkService = linkService;
    }

    @Override
    public ResponseEntity<ListLinksResponse> linksGet(Long tgChatId) {
        List<LinkResponse> links = linkService.getAllForChat(tgChatId)
                .stream()
                .map(link -> new LinkResponse(link.getId(), link.getUrl().toString()))
                .toList();
        return ResponseEntity.ok(new ListLinksResponse(links, links.size()));
    }

    @Override
    public ResponseEntity<LinkResponse> linksPost(Long tgChatId, AddLinkRequest body) {
        Link link = linkService.add(tgChatId, body.link());
        return ResponseEntity.ok(new LinkResponse(link.getId(), link.getUrl().toString()));
    }

    @Override
    public ResponseEntity<LinkResponse> linksDelete(Long tgChatId, RemoveLinkRequest body) {
        Link link = linkService.remove(tgChatId, body.link());
        return ResponseEntity.ok(new LinkResponse(link.getId(), link.getUrl().toString()));
    }
}
