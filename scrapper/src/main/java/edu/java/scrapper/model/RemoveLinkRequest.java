package edu.java.scrapper.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RemoveLinkRequest(
    @JsonProperty("link")
    String link
) {
}
