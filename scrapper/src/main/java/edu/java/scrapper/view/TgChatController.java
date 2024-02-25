package edu.java.scrapper.view;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TgChatController {
    @RequestMapping(
        path = "/hello",
        method = RequestMethod.GET,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hi!");
    }

    @RequestMapping(
        path = "/tg-chat/{id}",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> postTgChat(@PathVariable String id) {
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(
        path = "/tg-chat/{id}",
        method = RequestMethod.DELETE,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteTgChat(@PathVariable String id) {
        return ResponseEntity.badRequest().build();
    }
}
