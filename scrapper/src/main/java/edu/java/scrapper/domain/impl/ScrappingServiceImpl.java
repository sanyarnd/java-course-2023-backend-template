package edu.java.scrapper.domain.impl;

import edu.java.scrapper.data.db.LinkContentRepository;
import edu.java.scrapper.data.db.LinkRepository;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.network.ScrapperClient;
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
    private final List<ScrapperClient> scrapperClients;
    private final LinkRepository linkRepository;
    private final LinkContentRepository linkContentRepository;

    public ScrappingServiceImpl(
            List<ScrapperClient> scrapperClients,
            LinkRepository linkRepository,
            LinkContentRepository linkContentRepository
    ) {
        this.scrapperClients = scrapperClients;
        this.linkRepository = linkRepository;
        this.linkContentRepository = linkContentRepository;
    }

    private boolean validate(Link link) {
        return link != null && link.getId() != null && link.getUrl() != null;
    }

    private void process(Link link) {
        scrapperClients.stream()
                .filter(scrapperClient -> scrapperClient.canHandle(link.getUrl()))
                .findAny()
                .ifPresent(scrapperClient -> scrapperClient.handle(link.getUrl()));
    }

    /**
     * Schedule entrypoint for scrapping urls.
     */
    @Override
    @Scheduled(fixedDelayString = "#{@interval}")
    public void schedule() {
        try {
            OffsetDateTime leastUpdateTime = OffsetDateTime.now().minusMinutes(5);
            linkRepository.findAllLinksUpdatedBefore(leastUpdateTime)
                    .stream()
                    .filter(this::validate)
                    .forEach(this::process);
        } catch (Exception exception) {
            log.error("SCHEDULE_EX", exception);
        }
    }
}
