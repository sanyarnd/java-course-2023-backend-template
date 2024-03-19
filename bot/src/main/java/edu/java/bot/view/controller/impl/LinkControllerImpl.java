package edu.java.bot.view.controller.impl;

import edu.java.bot.view.controller.LinkController;
import edu.java.core.request.LinkUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LinkControllerImpl implements LinkController {
    @Override
    public ResponseEntity<Void> updatesPost(LinkUpdateRequest body) {
        log.info(String.valueOf(body));
        return ResponseEntity.ok().build();
    }
}
