package edu.java.scrapper.data.network;

import edu.java.core.exception.LinkIsUnreachable;
import edu.java.core.request.LinkUpdateRequest;
import edu.java.scrapper.data.db.entity.Link;
import java.util.regex.Pattern;

public interface ScrapperClient {
    Boolean canHandle(Link link);

    Link handle(Link link) throws LinkIsUnreachable;
}
