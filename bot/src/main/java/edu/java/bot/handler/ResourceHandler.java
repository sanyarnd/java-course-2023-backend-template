package edu.java.bot.handler;

public interface ResourceHandler {

    boolean canHandle(String resourceURL);

    boolean checkUpdates(String resourceURL);
}
