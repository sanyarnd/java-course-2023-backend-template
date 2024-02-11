package edu.java.bot.parser;

import edu.java.bot.website.WebsiteInfo;
import org.springframework.stereotype.Component;
import java.net.URI;

@Component
public class GitHubParser implements LinkParser {
    @Override
    public boolean parseLink(URI uri) {
        String host = uri.getHost();
        return host.equals(WebsiteInfo.GITHUB.getDomain());
    }
}
