package edu.java.scrapper.domain.impl;

import edu.java.core.request.LinkUpdateRequest;
import edu.java.scrapper.data.db.repository.LinkRepository;
import edu.java.scrapper.data.db.repository.BinderRepository;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.TelegramChat;
import edu.java.scrapper.data.network.BaseClient;
import edu.java.scrapper.data.network.NotificationConnector;
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
    private final BinderRepository binderRepository;
    private final NotificationConnector notificationConnector;

    public ScrappingServiceImpl(
            List<BaseClient> scrapperClients,
            LinkRepository linkRepository,
            BinderRepository binderRepository,
            NotificationConnector notificationConnector
    ) {
        this.scrapperClients = scrapperClients;
        this.linkRepository = linkRepository;
        this.binderRepository = binderRepository;
        this.notificationConnector = notificationConnector;
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
        String message = scrapperClient.handle(link);
        linkRepository.updateAndReturn(link.setLastUpdatedAt(OffsetDateTime.now()));
        notificationConnector.update(
                new LinkUpdateRequest(
                        link.getId(),
                        link.getUrl(),
                        message,
                        binderRepository.findAllChatsSubscribedTo(link)
                                .stream()
                                .map(TelegramChat::getId)
                                .toList()
                )
        );
    }

    @Override
    @Scheduled(fixedDelayString = "#{@interval}")
    public void schedule() {
        try {
            OffsetDateTime leastUpdateTime = OffsetDateTime.now().minusMinutes(MINUTES);
            linkRepository.getAllUpdatedBefore(leastUpdateTime)
                    .stream()
                    .filter(this::validate)
                    .forEach(this::process);
        } catch (Exception exception) {
            log.error(this.getClass().getName(), exception);
        }
    }
}
