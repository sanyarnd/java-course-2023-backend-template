package edu.java.payload.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record LinkUpdateRequest(
    @Min(value = 1, message = "Id should be greater than 0")
    long id,
    @NotEmpty(message = "Url should not be empty")
    String url,
    @NotEmpty(message = "Description should not be empty")
    String description,
    @NotEmpty(message = "Chat ids should not be empty")
    List<Long> tgChatIds) {
}
