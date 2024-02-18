package edu.java.bot;

import java.util.regex.Pattern;

public class LinkValidator {
    public String validate(String link) {
        if (isLinkCorrect(link)) {
            return "Link successfully added for tracking!";
        }
        return "Incorrect input";
    }

    public boolean isLinkCorrect(String link) {
        return Pattern.matches("^https:\\/\\/stackoverflow\\.com\\/questions\\/\\d+", link);
    }
}
