package edu.java.core.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RemoveLinkRequest(
    @JsonProperty("link")
    String link
) {
}
