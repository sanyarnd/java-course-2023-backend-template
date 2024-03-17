package edu.java.scrapper.domain.impl;

import edu.java.core.exception.LinkIsUnreachable;
import edu.java.core.exception.UserNotRegistered;
import edu.java.core.exception.link.LinkNotRegistered;
import edu.java.scrapper.data.db.LinkRepository;
import edu.java.scrapper.data.db.TelegramChatRepository;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.TelegramChat;
import edu.java.scrapper.domain.LinkService;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LinkServiceImpl implements LinkService {
    private final TelegramChatRepository telegramChatRepository;
    private final LinkRepository linkRepository;

    public LinkServiceImpl(TelegramChatRepository telegramChatRepository, LinkRepository linkRepository) {
        this.telegramChatRepository = telegramChatRepository;
        this.linkRepository = linkRepository;
    }

    @Override
    public Link add(Long telegramChatId, String url) {
        Link link = linkRepository.registerLink(new Link().setUrl(getAccess(url)).setLastUpdatedAt(OffsetDateTime.MIN));
        TelegramChat telegramChat = telegramChatRepository.findById(telegramChatId).orElseThrow(UserNotRegistered::new);
        linkRepository.subscribe(link, telegramChat);
        return link;
    }

    @Override
    public Link remove(Long telegramChatId, String url) {
        Link link = linkRepository.findByUrl(getAccess(url)).orElseThrow(LinkNotRegistered::new);
        TelegramChat telegramChat = telegramChatRepository.findById(telegramChatId).orElseThrow(UserNotRegistered::new);
        linkRepository.unsubscribe(link, telegramChat);
        return link;
    }

    @Override
    public List<Link> getAllForChat(Long chatId) {
        return linkRepository.findAllLinksSubscribedWith(new TelegramChat().setId(chatId));
    }

    private URL getAccess(String url) {
        try {
            return URI.create(url).toURL();
        } catch (MalformedURLException e) {
            throw new LinkIsUnreachable();
        }
    }
}
