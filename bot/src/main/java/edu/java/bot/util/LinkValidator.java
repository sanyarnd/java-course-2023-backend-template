package edu.java.bot.util;

import org.springframework.stereotype.Component;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

@Component
public class LinkValidator {
    private final Pattern linkPattern =
        Pattern.compile("^(https?|http)(:\\/\\/)([-a-zA-Z0-9+&@#\\/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#\\/%=~_|])");

    public URI validateLinkAndGetURI(String url) {
        try {
            if (linkPattern.matcher(url).matches()) {
                return new URI(url);
            }
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
