package edu.java.bot.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

@Component
public class LinkValidatorImpl implements LinkValidator {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Pattern linkPattern =
        Pattern.compile("^(https?|http)(:\\/\\/)([-a-zA-Z0-9+&@#\\/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#\\/%=~_|])");

    public URI validateLinkAndGetURI(String link) {
        try {
            if (linkPattern.matcher(link).matches()) {
                return new URI(link);
            }
            return null;
        } catch (URISyntaxException e) {
            LOGGER.info("Exception during link (%s) validation: \n%s".formatted(link, e.getMessage()));
            return null;
        }
    }
}
