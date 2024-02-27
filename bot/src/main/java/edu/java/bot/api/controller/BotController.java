package edu.java.bot.api.controller;

import edu.java.bot.api.controller.dto.request.LinkUpdateRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BotController {
    public void updatesPost(@Valid @RequestBody LinkUpdateRequest request) {
        log.info("new update request " + request.description());
    }
}
