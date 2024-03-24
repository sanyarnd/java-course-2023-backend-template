package edu.java.scrapper.data.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "link_content")
public class LinkContent {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "raw", nullable = false)
    private String raw;

    @Column(name = "hash", nullable = false)
    private Integer hash;
}
