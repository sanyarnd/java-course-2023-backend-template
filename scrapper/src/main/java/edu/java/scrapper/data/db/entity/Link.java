package edu.java.scrapper.data.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.net.URL;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Link {
    private Long id;
    private URL url;
    private OffsetDateTime lastUpdatedAt;
}
