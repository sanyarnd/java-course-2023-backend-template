package edu.java.bot.util;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class UrlChecker {

    private UrlChecker() {

    }

    @SuppressWarnings("MagicNumber")
    public static boolean checkUrlExists(String resourceUrl) {
        try {
            URL url = URI.create(resourceUrl).toURL();
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            connect.setRequestMethod("HEAD");
            connect.connect();
            int responseCode = connect.getResponseCode();
            return (200 <= responseCode && responseCode <= 399);
        } catch (Exception e) {
            return false;
        }
    }
}
