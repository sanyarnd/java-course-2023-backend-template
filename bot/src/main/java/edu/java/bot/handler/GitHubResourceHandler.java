package edu.java.bot.handler;

import org.springframework.stereotype.Component;

@Component
public class GitHubResourceHandler implements ResourceHandler {

    @Override
    public boolean canHandle(String resourceURL) {
        return resourceURL.contains("github.com");
    }

    @Override
    public boolean checkUpdates(String resourceURL) {
        return true;
    }
}
