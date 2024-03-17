package edu.java.services.jdbc;

import edu.java.controller.dto.LinkResponse;
import edu.java.controller.exception.ChatNotFoundException;
import edu.java.domain.JdbcChatsDAO;
import edu.java.domain.JdbcLinksDAO;
import edu.java.domain.JdbcSubscribesDAO;
import edu.java.domain.dto.LinkDTO;
import edu.java.domain.dto.SubscribeDTO;
import edu.java.services.interfaces.SubscribeService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class JdbcSubscribeService implements SubscribeService {

    JdbcChatsDAO chatRepository;
    JdbcLinksDAO linkRepository;
    JdbcSubscribesDAO subscribesRepository;

    public JdbcSubscribeService(
        JdbcChatsDAO chatRepository,
        JdbcLinksDAO linkRepository,
        JdbcSubscribesDAO subscribesRepository
    ) {
        this.chatRepository = chatRepository;
        this.linkRepository = linkRepository;
        this.subscribesRepository = subscribesRepository;
    }

    @Override
    public List<LinkResponse> getTrackedURLs(Long chatId) {
        checkChatInSystem(chatId);
        List<SubscribeDTO> subscribes = subscribesRepository.findAll(chatId);
        List<LinkDTO> links = linkRepository.findAll();
        //subscribes.stream().map(subscribeDTO -> )
        return null;
    }

    @Override
    public void addTrackedURLs(Long chatId, String providedURL) {

    }

    @Override
    public void removeTrackedURLs(Long chatId, String providedURL) {

    }

    private void checkChatInSystem(Long chatId) {
        if (!chatRepository.contains(chatId)) {
            throw new ChatNotFoundException("Нет чата с id: " + chatId);
        }
    }
}
