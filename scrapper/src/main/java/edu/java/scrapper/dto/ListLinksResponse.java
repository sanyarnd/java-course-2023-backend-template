package edu.java.scrapper.dto;

import java.util.List;

public record ListLinksResponse(
    int size,
    List<LinkResponse> links
) {
    public void addLinkResponse(LinkResponse link){
        links.add(link);
    }

    public void deleteLinkResponse(LinkResponse link){
        links.remove(link);
    }
}
