package edu.java.scrapper.controller;

import edu.java.scrapper.dto.AddLinkRequest;
import edu.java.scrapper.dto.LinkResponse;
import edu.java.scrapper.dto.ListLinksResponse;
import edu.java.scrapper.dto.RemoveLinkRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import edu.java.scrapper.service.ScrapperService;

@RestController
public class ScrapperController {
    private ScrapperService service;

    @Autowired
    public ScrapperController(ScrapperService service) {
        this.service = service;
    }

    @GetMapping("/links")
    public ListLinksResponse getLinks(@RequestHeader("Tg-Chat-Id") long chatId) {
        return service.getLinks(chatId);
    }

    @PostMapping("/links")
    public LinkResponse addLink(@RequestHeader("Tg-Chat-Id") long chatId, @RequestBody AddLinkRequest request){
        LinkResponse link = new LinkResponse(chatId, request.link());
        return service.addLink(chatId, link);
    }

    @DeleteMapping("/links")
    public LinkResponse deleteLink(@RequestHeader("Tg-Chat-Id") Long chatId, @RequestBody RemoveLinkRequest request) {
        LinkResponse link = new LinkResponse(chatId, request.link());
        return service.deleteLink(chatId, link);
    }

    @PostMapping("/tg-chat/{id}")
    public void registerChat(@PathVariable("id") Long id) {
        service.registerChat(id);
    }

    @DeleteMapping("/tg-chat/{id}")
    public void deleteChat(@PathVariable("id") Long id) {
        service.deleteChat(id);
    }
}
