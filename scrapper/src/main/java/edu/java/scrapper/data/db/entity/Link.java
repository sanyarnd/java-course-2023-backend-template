package edu.java.scrapper.data.db.entity;

import java.net.URL;
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
    private URL url;
    private OffsetDateTime lastUpdatedAt;
}
