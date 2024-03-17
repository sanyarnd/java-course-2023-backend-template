package edu.java.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class LinksDTO {
    
    Long id;
    String url;
    OffsetDateTime createdAt;

    public LinksDTO(String url) {
        this.url = url;
    }
}
