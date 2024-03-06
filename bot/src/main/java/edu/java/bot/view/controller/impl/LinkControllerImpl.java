package edu.java.bot.view.controller.impl;

import edu.java.bot.view.controller.LinkController;
import edu.java.core.request.LinkUpdateRequest;
import edu.java.core.util.ResponseStubber;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinkControllerImpl implements LinkController {
    @Override
    public ResponseEntity<Void> updatesPost(LinkUpdateRequest body) {
        return new ResponseEntity<>(ResponseStubber.stubNotImplemented());
    }
}
