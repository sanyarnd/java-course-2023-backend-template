package edu.java.bot.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

import java.util.List;

public record ListLinksResponse(
    @JsonProperty("links")
    @NonNull
    List<LinkResponse> links,

    @JsonProperty("size")
    @NonNull
    Integer size
) {
}
