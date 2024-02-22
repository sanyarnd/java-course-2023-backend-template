package edu.java.bot.domain;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Link {
    // todo: добавить нормальную БД с many to many между Link и Chat
    private String domain;
    private String url;

    public Link(String domain, String url) {
        this.domain = domain;
        this.url = url;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static Link parse(String url) throws URISyntaxException, MalformedURLException {
        URL parsed = new URI(url).toURL();
        return new Link(parsed.getHost(), url);
    }

    @Override
    public String toString() {
        return url;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Link that)) {
            return false;
        }
        return that.getUrl().equals(url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }
}
