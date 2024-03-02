package edu.java.scrapper.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddLinkRequest(
    @JsonProperty("link")
    String link
) {
}
