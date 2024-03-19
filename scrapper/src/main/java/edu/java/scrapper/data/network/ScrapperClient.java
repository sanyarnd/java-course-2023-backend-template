package edu.java.scrapper.data.network;

import edu.java.core.exception.LinkIsUnreachable;
import edu.java.scrapper.data.db.entity.Link;

public interface ScrapperClient {
    Boolean canHandle(Link link);

    Link handle(Link link) throws LinkIsUnreachable;
}
