package edu.java.bot.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Links {

    private static final List<String> ALLOWED_SITES = new ArrayList<>();
    private static final Logger LOGGER = LogManager.getLogger();

    static {
        ALLOWED_SITES.add("https://github.com/");
        ALLOWED_SITES.add("https://stackoverflow.com/");
    }

    private Links() {
    }

    public static boolean isValid(String link) {
        boolean valid = false;
        for (String allowedDomain : ALLOWED_SITES) {
            valid = valid || link.startsWith(allowedDomain);
        }
        return valid;
    }

    public static Map<String, List<String>> splitByDomain(List<String> links) {
        Map<String, List<String>> map = new TreeMap<>();
        if (Objects.isNull(links)) {
            return null;
        }
        for (var link : links) {
            try {
                URL parsedUrl = new URL(link);
                String domain = parsedUrl.getHost();
                if (map.containsKey(domain)) {
                    map.get(domain).add(link);
                } else {
                    List<String> newList = new ArrayList<>();
                    newList.add(link);
                    map.put(domain, newList);
                }
            } catch (MalformedURLException e) {
                LOGGER.error(e);
            }
        }
        return map;
    }
}
