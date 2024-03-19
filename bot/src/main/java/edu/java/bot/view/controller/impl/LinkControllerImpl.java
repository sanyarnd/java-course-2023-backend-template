package edu.java.bot.view.controller.impl;

import edu.java.bot.view.controller.LinkController;
import edu.java.core.request.LinkUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinkControllerImpl implements LinkController {
    @Override
    public ResponseEntity<Void> updatesPost(LinkUpdateRequest body) {
        System.out.println(body);
        return ResponseEntity.ok().build();
    }
}
