package edu.java.scrapper.view.impl;

import edu.java.scrapper.model.AddLinkRequest;
import edu.java.scrapper.model.LinkResponse;
import edu.java.scrapper.model.ListLinksResponse;
import edu.java.scrapper.model.RemoveLinkRequest;
import edu.java.scrapper.view.LinkController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinkControllerImpl implements LinkController {
    @Override
    public ResponseEntity<ListLinksResponse> linksGet(Long tgChatId) {
        return null;
    }

    @Override
    public ResponseEntity<LinkResponse> linksPost(Long tgChatId, AddLinkRequest body) {
        return null;
    }

    @Override
    public ResponseEntity<LinkResponse> linksDelete(Long tgChatId, RemoveLinkRequest body) {
        return null;
    }
}
