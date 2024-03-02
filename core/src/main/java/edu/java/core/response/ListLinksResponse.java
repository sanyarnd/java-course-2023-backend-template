package edu.java.core.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record ListLinksResponse(
    @JsonProperty("links")
    List<LinkResponse> links,

    @JsonProperty("size")
    Integer size
) {
}
