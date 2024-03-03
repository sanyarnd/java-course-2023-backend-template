package edu.java.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import java.net.URI;

@Data
public class AddLinkRequest {
    @URL
    String link;
}
