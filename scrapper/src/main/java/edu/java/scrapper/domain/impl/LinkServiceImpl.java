package edu.java.scrapper.domain.impl;

import edu.java.core.exception.LinkIsUnreachable;
import edu.java.core.exception.UserNotRegistered;
import edu.java.core.exception.link.LinkNotRegistered;
import edu.java.scrapper.data.db.LinkRepository;
import edu.java.scrapper.data.db.TelegramChatRepository;
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

    public LinkServiceImpl(
            List<BaseClient> scrapperClients,
            TelegramChatRepository telegramChatRepository,
            LinkRepository linkRepository
    ) {
        this.scrapperClients = scrapperClients;
        this.telegramChatRepository = telegramChatRepository;
        this.linkRepository = linkRepository;
    }

    @Override
    public Link add(Long telegramChatId, String url) {
        TelegramChat telegramChat = telegramChatRepository.findById(telegramChatId).orElseThrow(UserNotRegistered::new);
        if (scrapperClients.stream().noneMatch(baseClient -> baseClient.canHandle(url))) {
            throw new LinkIsUnreachable();
        }
        Link link = linkRepository.registerLink(new Link().setUrl(url).setLastUpdatedAt(OffsetDateTime.MIN));
        linkRepository.subscribe(link, telegramChat);
        return link;
    }

    @Override
    public Link remove(Long telegramChatId, String url) {
        Link link = linkRepository.findByUrl(url).orElseThrow(LinkNotRegistered::new);
        TelegramChat telegramChat = telegramChatRepository.findById(telegramChatId).orElseThrow(UserNotRegistered::new);
        linkRepository.unsubscribe(link, telegramChat);
        return link;
    }

    @Override
    public List<Link> getAllForChat(Long chatId) {
        return linkRepository.findAllLinksSubscribedWith(new TelegramChat().setId(chatId));
    }
}
