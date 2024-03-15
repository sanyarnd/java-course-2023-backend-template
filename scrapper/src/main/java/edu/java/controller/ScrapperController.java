package edu.java.controller;

import edu.java.controller.api.ScrapperApi;
import edu.java.payload.dto.request.AddLinkRequest;
import edu.java.payload.dto.request.RemoveLinkRequest;
import edu.java.payload.dto.response.LinkResponse;
import edu.java.payload.dto.response.ListLinksResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ScrapperController implements ScrapperApi {

    @Override
    public ListLinksResponse linksGet(Long tgChatId) {
        log.info("Links get request for chat {}", tgChatId);
        return null;
    }

    @Override
    public LinkResponse linksPost(Long tgChatId, AddLinkRequest body) {
        log.info("Add link request link: {} to chat id: {}", body.link(), tgChatId);
        return null;
    }

    @Override
    public LinkResponse linksDelete(Long tgChatId, RemoveLinkRequest body) {
        log.info("Remove link request {} from chat id: {}", body.link(), tgChatId);
        return null;
    }

    @Override
    public void tgChatIdDelete(Long id) {
        log.info("Delete tg chat {}", id);
    }

    @Override
    public void tgChatIdPost(Long id) {
        log.info("Add tg chat {}", id);
    }
}
