package edu.java.scrapper.data.db.entity;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Link {
    private Long id;
    private String url;
    private OffsetDateTime lastUpdatedAt;
}
