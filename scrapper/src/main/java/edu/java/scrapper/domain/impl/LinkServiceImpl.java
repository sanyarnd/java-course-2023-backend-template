package edu.java.scrapper.domain.impl;

import edu.java.core.exception.LinkAlreadyTrackedException;
import edu.java.core.exception.LinkCannotBeHandledException;
import edu.java.core.exception.LinkIsNotRegisteredException;
import edu.java.core.exception.LinkIsNotTrackedException;
import edu.java.core.exception.UserIsNotAuthorizedException;
import edu.java.scrapper.data.db.repository.LinkRepository;
import edu.java.scrapper.data.db.repository.TelegramChatRepository;
import edu.java.scrapper.data.db.repository.BinderRepository;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.TelegramChat;
import edu.java.scrapper.data.network.BaseClient;
import edu.java.scrapper.domain.LinkService;
import java.time.OffsetDateTime;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

@Service
public class LinkServiceImpl implements LinkService {
    private final List<BaseClient> scrapperClients;
    private final TelegramChatRepository telegramChatRepository;
    private final LinkRepository linkRepository;
    private final BinderRepository binderRepository;

    public LinkServiceImpl(
            List<BaseClient> scrapperClients,
            TelegramChatRepository telegramChatRepository,
            LinkRepository linkRepository,
            BinderRepository binderRepository
    ) {
        this.scrapperClients = scrapperClients;
        this.telegramChatRepository = telegramChatRepository;
        this.linkRepository = linkRepository;
        this.binderRepository = binderRepository;
    }

    @Override
    public Link add(Long telegramChatId, String url)
            throws LinkCannotBeHandledException, LinkAlreadyTrackedException, LinkIsNotRegisteredException,
            UserIsNotAuthorizedException {
        validateUrl(url);
        Link link = linkRepository
                .upsertAndReturn(new Link().setUrl(url).setLastUpdatedAt(OffsetDateTime.now()));
        TelegramChat telegramChat = telegramChatRepository
                .get(telegramChatId)
                .orElseThrow(() -> new UserIsNotAuthorizedException(telegramChatId));
        binderRepository.create(Pair.of(telegramChat, link));
        return link;
    }

    @Override
    public Link remove(Long telegramChatId, String url)
            throws LinkIsNotTrackedException, LinkIsNotRegisteredException, UserIsNotAuthorizedException {
        Link link = linkRepository
                .getByUrl(url)
                .orElseThrow(() -> new LinkIsNotRegisteredException(url));
        TelegramChat telegramChat = telegramChatRepository
                .get(telegramChatId)
                .orElseThrow(() -> new UserIsNotAuthorizedException(telegramChatId));
        binderRepository.delete(Pair.of(telegramChat, link));
        return link;
    }

    @Override
    public List<Link> getAllForChat(Long chatId) {
        return binderRepository.findAllLinksSubscribedWith(new TelegramChat().setId(chatId));
    }

    private void validateUrl(String url) throws LinkCannotBeHandledException {
        scrapperClients.stream()
                .filter(baseClient -> baseClient.canHandle(url))
                .findAny()
                .orElseThrow(() -> new LinkCannotBeHandledException(url));
    }
}
