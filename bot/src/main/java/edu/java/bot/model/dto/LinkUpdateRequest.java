package edu.java.bot.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record LinkUpdateRequest(
    @JsonProperty("id")
    Long id,

    @JsonProperty("url")
    String url,

    @JsonProperty("description")
    String description,

    @JsonProperty("tgChatIds")
    List<Long> tgChatIds
) {
}
