package edu.java.bot.bot;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Getter
@Component
@ConfigurationProperties(prefix = "app.telegram-setts")
@Data
@Validated
public class NireblTinkoffCourseBotProperties {
    @NotEmpty
    private String token;

    private int botOwner;
}
