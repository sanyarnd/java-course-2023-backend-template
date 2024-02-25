package edu.java.scrapper.data.dto.scrapper;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LinkResponse(
    @JsonProperty("id")
    Long id,
    @JsonProperty("url")
    String url
) {
}
