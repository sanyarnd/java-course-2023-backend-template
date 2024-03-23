package edu.java.bot.view.controller.impl;

import edu.java.bot.domain.NotifyUserForUpdatesUseCase;
import edu.java.bot.view.controller.LinkController;
import edu.java.core.request.LinkUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class LinkControllerImpl implements LinkController {
    private final NotifyUserForUpdatesUseCase notifyUser;

    @Override
    public ResponseEntity<Void> updatesPost(LinkUpdateRequest body) {
        body.tgChatIds().forEach(userId -> notifyUser.notify(userId, body.description(), body.url()));
        return ResponseEntity.ok().build();
    }
}
