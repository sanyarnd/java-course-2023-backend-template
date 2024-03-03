package edu.java.controller.dto;

import lombok.Data;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Data
public class ListLinkResponse {
    List<LinkResponse> links;
    Integer size;

    public ListLinkResponse(List<URI> urls) {
        this.size = urls.size();
        this.links = new ArrayList<>();
        for (int i = 0; i < this.size; i++) {
            links.add(new LinkResponse((long) i, urls.get(i)));
        }
    }
}
