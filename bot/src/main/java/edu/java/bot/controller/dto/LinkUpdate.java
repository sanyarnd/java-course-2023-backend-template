package edu.java.bot.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@Data
public class LinkUpdate {
    Long id;

    @NotEmpty
    String url;

    String description;

    @NotEmpty
    List<Long> tgChatIds;
}
