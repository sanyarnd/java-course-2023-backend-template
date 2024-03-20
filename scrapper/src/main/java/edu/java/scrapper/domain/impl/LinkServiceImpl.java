package edu.java.scrapper.domain.impl;

import edu.java.core.exception.*;
import edu.java.scrapper.data.db.LinkRepository;
import edu.java.scrapper.data.db.TelegramChatRepository;
import edu.java.scrapper.data.db.TrackerRepository;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.TelegramChat;
import edu.java.scrapper.data.network.BaseClient;
import edu.java.scrapper.domain.LinkService;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LinkServiceImpl implements LinkService {
    private final List<BaseClient> scrapperClients;
    private final TelegramChatRepository telegramChatRepository;
    private final LinkRepository linkRepository;
    private final TrackerRepository trackerRepository;

    public LinkServiceImpl(
            List<BaseClient> scrapperClients,
            TelegramChatRepository telegramChatRepository,
            LinkRepository linkRepository,
            TrackerRepository trackerRepository
    ) {
        this.scrapperClients = scrapperClients;
        this.telegramChatRepository = telegramChatRepository;
        this.linkRepository = linkRepository;
        this.trackerRepository = trackerRepository;
    }

    private void validateUrl(String url) throws LinkCannotBeHandledException {
        scrapperClients.stream()
                .filter(baseClient -> baseClient.canHandle(url))
                .findAny()
                .orElseThrow(() -> new LinkCannotBeHandledException(url));
    }

    @Override
    public Link add(Long telegramChatId, String url)
            throws LinkCannotBeHandledException, LinkAlreadyTrackedException, LinkIsNotRegisteredException, UserIsNotAuthorizedException {
        validateUrl(url);
        Link link = linkRepository
                .addOrGetExisted(new Link().setUrl(url).setLastUpdatedAt(OffsetDateTime.MIN));
        TelegramChat telegramChat = telegramChatRepository
                .findById(telegramChatId)
                .orElseThrow(() -> new UserIsNotAuthorizedException(telegramChatId));
        trackerRepository.track(link, telegramChat);
        return link;
    }

    @Override
    public Link remove(Long telegramChatId, String url)
            throws LinkIsNotTrackedException, LinkIsNotRegisteredException, UserIsNotAuthorizedException {
        Link link = linkRepository
                .findByUrl(url)
                .orElseThrow(() -> new LinkIsNotRegisteredException(url));
        TelegramChat telegramChat = telegramChatRepository
                .findById(telegramChatId)
                .orElseThrow(() -> new UserIsNotAuthorizedException(telegramChatId));
        trackerRepository.untrack(link, telegramChat);
        return link;
    }

    @Override
    public List<Link> getAllForChat(Long chatId) {
        return trackerRepository.findAllLinksSubscribedWith(new TelegramChat().setId(chatId));
    }
}
