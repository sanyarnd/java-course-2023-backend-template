package edu.java.scrapper.data.dto.scrapper;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RemoveLinkRequest(
    @JsonProperty("link")
    String link
) {
}
