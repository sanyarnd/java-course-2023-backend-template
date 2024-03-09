package edu.java.controller.dto;

import java.net.URI;
import lombok.Data;

@Data
public class LinkResponse {
    Long id;
    URI url;

    public LinkResponse(Long id, URI url) {
        this.id = id;
        this.url = url;
    }
}
