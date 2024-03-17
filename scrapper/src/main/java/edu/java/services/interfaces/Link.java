package edu.java.services.interfaces;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Link {
    Long id;
    String url;

    public Link(String url) {
        this.url = url;
    }
}
