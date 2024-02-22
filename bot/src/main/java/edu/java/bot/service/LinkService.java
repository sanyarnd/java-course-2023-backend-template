package edu.java.bot.service;

import edu.java.bot.domain.Link;
import edu.java.bot.repository.LinkRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class LinkService {
    private final List<String> supportedDomains = List.of("github.com", "stackoverflow.com");
    private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public List<String> getSupportedDomains() {
        return supportedDomains;
    }

    public boolean isSupported(Link link) {
        return supportedDomains.contains(link.getDomain());
    }

    public void addLink(long id, Link link) {
        linkRepository.addLink(id, link);
    }

    public Optional<Link> find(long id, String url) {
        return linkRepository.find(id, url);
    }

    public void deleteLink(long chatId, Link link) {
        linkRepository.deleteLink(chatId, link);
    }

    public List<Link> findAll(long chatId) {
        return linkRepository.findAll(chatId);
    }
}
