package edu.java.scrapper.domain.impl;

import edu.java.core.request.LinkUpdateRequest;
import edu.java.scrapper.data.db.LinkRepository;
import edu.java.scrapper.data.db.TrackerRepository;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.TelegramChat;
import edu.java.scrapper.data.network.BaseClient;
import edu.java.scrapper.data.network.NotificationConnector;
import edu.java.scrapper.domain.ScrappingService;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
    private final TrackerRepository trackerRepository;
    private final NotificationConnector notificationConnector;

    public ScrappingServiceImpl(
            List<BaseClient> scrapperClients,
            LinkRepository linkRepository,
            TrackerRepository trackerRepository, NotificationConnector notificationConnector
    ) {
        this.scrapperClients = scrapperClients;
        this.linkRepository = linkRepository;
        this.trackerRepository = trackerRepository;
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
        linkRepository.updateTimeTouched(link.setLastUpdatedAt(OffsetDateTime.now()));
        notificationConnector.update(
                new LinkUpdateRequest(
                        link.getId(),
                        link.getUrl(),
                        message,
                        trackerRepository.findAllChatsSubscribedTo(link)
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
            linkRepository.findAllLinksUpdatedBefore(leastUpdateTime)
                    .stream()
                    .filter(this::validate)
                    .forEach(this::process);
        } catch (Exception exception) {
            log.error(this.getClass().getName(), exception);
        }
    }
}
