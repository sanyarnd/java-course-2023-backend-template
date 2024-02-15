package edu.java.bot.parser;

import edu.java.bot.website.WebsiteInfo;
import java.net.URI;
import org.springframework.stereotype.Component;

@Component
public class StackOverflowParser implements LinkParser {
    @Override
    public boolean parseLink(URI uri) {
        String host = uri.getHost();
        if (host != null) {
            return host.equals(WebsiteInfo.STACK_OVERFLOW.getDomain());
        }
        return false;
    }
}
