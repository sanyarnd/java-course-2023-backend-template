package edu.java.controller.dto;

import lombok.Data;
import org.hibernate.validator.constraints.URL;
import java.net.URI;

@Data
public class RemoveLinkRequest {
    @URL
    String link;
}
