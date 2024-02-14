package edu.java.bot.service.impl;

import edu.java.bot.handler.ResourceHandler;
import edu.java.bot.repository.UrlRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static edu.java.bot.util.UrlChecker.checkUrlExists;

@Service
public class UpdateService {
    private final List<ResourceHandler> handlers;
    private final UrlRepository urlRepository = UrlRepository.getInstance();

    @Autowired
    public UpdateService(List<ResourceHandler> handlers) {
        this.handlers = handlers;
    }

    public boolean checkResourceURL(Long chatId, String resourceURL) {
        if (canHandle(resourceURL) && checkUrlExists(resourceURL)) {
            return urlRepository.addUrl(chatId, resourceURL);
        }
        return false;
    }

    public void checkUpdates(String resourceURL) {
        for (ResourceHandler handler : handlers) {
            if (handler.checkUpdates(resourceURL)) {
                // ToDo дописать лог и шедуллер для вызова
                break;
            }
        }
    }

    private boolean canHandle(String url) {
        for (ResourceHandler resourceHandler : handlers) {
            if (resourceHandler.canHandle(url)) {
                return true;
            }
        }
        return false;
    }
}
