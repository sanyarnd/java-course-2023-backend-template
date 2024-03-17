package edu.java.controller.dto;

import java.util.List;
import lombok.Data;

@Data
public class ListLinkResponse {
    List<LinkResponse> links;
    Integer size;

    public ListLinkResponse(List<LinkResponse> urls) {
        this.size = urls.size();
        this.links = urls;
    }
}
