package edu.java.bot.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
@AllArgsConstructor
public class AddLinkRequest {
    @URL
    String link;
}
