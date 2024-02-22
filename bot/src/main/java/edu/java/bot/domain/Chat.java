package edu.java.bot.domain;

import java.util.List;

public class Chat {
    private long id;
    private List<Link> links;

    public Chat(long id, List<Link> links) {
        this.id = id;
        this.links = links;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void addLink(Link link) {
        links.add(link);
    }
}
