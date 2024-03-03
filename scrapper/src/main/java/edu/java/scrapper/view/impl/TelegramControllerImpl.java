package edu.java.scrapper.view.impl;

import edu.java.core.util.ResponseStubber;
import edu.java.scrapper.view.TelegramController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TelegramControllerImpl implements TelegramController {
    @Override
    public ResponseEntity<Void> tgChatIdPost(Long id) {
        return new ResponseEntity<>(ResponseStubber.stubNotImplemented());
    }

    @Override
    public ResponseEntity<Void> tgChatIdDelete(Long id) {
        return new ResponseEntity<>(ResponseStubber.stubNotImplemented());
    }
}
