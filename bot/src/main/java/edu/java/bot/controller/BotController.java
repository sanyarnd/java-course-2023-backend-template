package edu.java.bot.controller;

import edu.java.bot.dto.LinkUpdate;
import org.apache.logging.log4j.LogManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
public class BotController {
    private static final Logger LOGGER = LogManager.getLogger();
    @PostMapping("/updates")
    public void processUpdate(@RequestBody LinkUpdate linkUpdateRequest) {
        LOGGER.info("updates are added");
    }
}
