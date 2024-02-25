package edu.java.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class Repository {

    @JsonProperty("id")
    long id;
    @JsonProperty("name")
    String name;
    Owner owner;
    @JsonProperty("updated_at")
    OffsetDateTime updatedAt;
    @JsonProperty("pushed_at")
    OffsetDateTime pushedAt;
}
