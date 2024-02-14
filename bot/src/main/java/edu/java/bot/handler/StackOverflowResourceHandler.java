package edu.java.bot.handler;

import org.springframework.stereotype.Component;

@Component
public class StackOverflowResourceHandler implements ResourceHandler {

    @Override
    public boolean canHandle(String resourceURL) {
        return resourceURL.contains("stackoverflow.com");
    }

    @Override
    public boolean checkUpdates(String resourceURL) {
        return true;
    }
}
