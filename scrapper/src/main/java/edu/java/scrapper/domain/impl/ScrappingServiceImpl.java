package edu.java.scrapper.domain.impl;

import edu.java.core.exception.LinkCannotBeHandledException;
import edu.java.core.request.LinkUpdateRequest;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.TelegramChat;
import edu.java.scrapper.data.db.repository.BindingRepository;
import edu.java.scrapper.data.db.repository.LinkRepository;
import edu.java.scrapper.data.network.BaseClient;
import edu.java.scrapper.data.network.NotificationConnector;
import edu.java.scrapper.domain.ScrappingService;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@ConditionalOnProperty(value = "app.scheduler.enable", havingValue = "true", matchIfMissing = true)
public class ScrappingServiceImpl implements ScrappingService {
    private final List<BaseClient> scrapperClients;
    private final LinkRepository linkRepository;
    private final BindingRepository bindingRepository;
    private final NotificationConnector notificationConnector;
    private final Duration linkExpiration;

    public ScrappingServiceImpl(
            List<BaseClient> scrapperClients,
            LinkRepository linkRepository,
            BindingRepository bindingRepository,
            NotificationConnector notificationConnector,
            @Qualifier("expiration") Duration linkExpiration
    ) {
        this.scrapperClients = scrapperClients;
        this.linkRepository = linkRepository;
        this.bindingRepository = bindingRepository;
        this.notificationConnector = notificationConnector;
        this.linkExpiration = linkExpiration;
    }

    @Override
    @Scheduled(fixedDelayString = "#{@interval}")
    public void schedule() {
        try {
            OffsetDateTime leastUpdateTime = OffsetDateTime.now().minusSeconds(linkExpiration.toSeconds());
            linkRepository.getAllUpdatedBefore(leastUpdateTime)
                    .stream()
                    .filter(this::validate)
                    .forEach(this::process);
        } catch (Exception exception) {
            log.error(this.getClass().getName(), exception);
        }
    }

    private boolean validate(Link link) {
        return link.getUrl() != null && !link.getUrl().isBlank();
    }

    private void process(Link link) {
        try {
            scrapperClients.stream()
                    .filter(scrapperClient -> scrapperClient.canHandle(link))
                    .findAny()
                    .ifPresentOrElse(
                            scrapperClient -> update(link, scrapperClient),
                            () -> log.warn("No action for: " + link)
                    );
        } catch (LinkCannotBeHandledException exception) {
            log.warn("Can't handle: " + link);
        }
    }

    private void update(Link link, BaseClient scrapperClient) throws LinkCannotBeHandledException {
        // Handle Link and get diff-message
        String message = scrapperClient.handle(link);
        // Update Link time
        linkRepository.updateAndReturn(link.setLastUpdatedAt(OffsetDateTime.now()));
        // Send if updated
        if (message != null) {
            notificationConnector.update(
                    new LinkUpdateRequest(
                            link.getId(),
                            link.getUrl(),
                            message,
                            bindingRepository.findAllChatsSubscribedTo(link)
                                    .stream()
                                    .map(TelegramChat::getId)
                                    .toList()
                    )
            );
        }
    }
}
