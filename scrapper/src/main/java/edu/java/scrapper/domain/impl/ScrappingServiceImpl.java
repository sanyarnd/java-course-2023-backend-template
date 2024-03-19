package edu.java.scrapper.domain.impl;

import edu.java.core.exception.LinkIsUnreachable;
import edu.java.core.exception.UnrecognizableException;
import edu.java.core.request.LinkUpdateRequest;
import edu.java.scrapper.data.db.LinkRepository;
import edu.java.scrapper.data.db.TelegramChatRepository;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.TelegramChat;
import edu.java.scrapper.data.network.NotificationRepository;
import edu.java.scrapper.data.network.BaseClient;
import edu.java.scrapper.domain.ScrappingService;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@ConditionalOnProperty(value = "app.scheduler.enable", havingValue = "true", matchIfMissing = true)
public class ScrappingServiceImpl implements ScrappingService {
    private final static Integer MINUTES = 10;
    private final List<BaseClient> scrapperClients;
    private final LinkRepository linkRepository;
    private final TelegramChatRepository telegramChatRepository;
    private final NotificationRepository notificationRepository;

    public ScrappingServiceImpl(
            List<BaseClient> scrapperClients,
            LinkRepository linkRepository,
            TelegramChatRepository telegramChatRepository,
            NotificationRepository notificationRepository
    ) {
        this.scrapperClients = scrapperClients;
        this.linkRepository = linkRepository;
        this.telegramChatRepository = telegramChatRepository;
        this.notificationRepository = notificationRepository;
    }

    private boolean validate(Link link) {
        return link.getUrl() != null && !link.getUrl().isBlank();
    }

    private void process(Link link) {
        scrapperClients.stream()
                .filter(scrapperClient -> scrapperClient.canHandle(link))
                .findAny()
                .ifPresentOrElse(
                        scrapperClient -> update(link, scrapperClient),
                        () -> log.warn("No action for: " + link)
                );
    }

    private void update(Link link, BaseClient scrapperClient) {
        try {
            Link updatedLink = scrapperClient.handle(link);
            linkRepository.update(updatedLink);
            notificationRepository.update(
                    new LinkUpdateRequest(
                            link.getId(),
                            link.getUrl(),
                            "",
                            telegramChatRepository.findAllChatsSubscribedTo(updatedLink)
                                    .stream()
                                    .map(TelegramChat::getId)
                                    .toList()
                    )
            );
        } catch (LinkIsUnreachable exception) {
            log.warn("SCRAPPER", exception);
        } catch (UnrecognizableException exception) {
            log.warn("IDK");
        }
    }

    /**
     * Schedule entrypoint for scrapping urls.
     */
    @Override
    @Scheduled(fixedDelayString = "#{@interval}")
    public void schedule() {
        try {
            OffsetDateTime leastUpdateTime = OffsetDateTime.now().minusMinutes(MINUTES);
            linkRepository.findAllLinksUpdatedBefore(leastUpdateTime)
                    .stream()
                    .filter(this::validate)
                    .forEach(this::process);
        } catch (Exception exception) {
            log.error("SCHEDULE_EX", exception);
        }
    }
}
