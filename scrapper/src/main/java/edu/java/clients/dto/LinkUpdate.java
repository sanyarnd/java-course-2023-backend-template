package edu.java.clients.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LinkUpdate {
    Long id;

    @NotEmpty
    String url;

    String description;

    @NotEmpty
    List<Long> tgChatIds;
}
