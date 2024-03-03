package edu.java.scrapper.view.impl;

import edu.java.core.request.AddLinkRequest;
import edu.java.core.request.RemoveLinkRequest;
import edu.java.core.response.LinkResponse;
import edu.java.core.response.ListLinksResponse;
import edu.java.core.util.ResponseStubber;
import edu.java.scrapper.view.LinkController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinkControllerImpl implements LinkController {
    @Override
    public ResponseEntity<ListLinksResponse> linksGet(Long tgChatId) {
        return new ResponseEntity<>(ResponseStubber.stubNotImplemented());
    }

    @Override
    public ResponseEntity<LinkResponse> linksPost(Long tgChatId, AddLinkRequest body) {
        return new ResponseEntity<>(ResponseStubber.stubNotImplemented());
    }

    @Override
    public ResponseEntity<LinkResponse> linksDelete(Long tgChatId, RemoveLinkRequest body) {
        return new ResponseEntity<>(ResponseStubber.stubNotImplemented());
    }
}
