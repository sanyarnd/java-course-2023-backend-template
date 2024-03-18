package edu.java.scrapper.data.network;

import edu.java.core.exception.LinkIsUnreachable;
import edu.java.core.request.LinkUpdateRequest;
import java.util.regex.Pattern;

public interface ScrapperClient {
    Pattern pathPattern();

    Boolean canHandle(String url);

    LinkUpdateRequest handle(String url) throws LinkIsUnreachable;
}
