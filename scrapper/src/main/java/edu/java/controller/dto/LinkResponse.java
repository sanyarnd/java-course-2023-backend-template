package edu.java.controller.dto;

import lombok.Data;
import java.net.URI;
import java.net.URISyntaxException;

@Data
public class LinkResponse {
    // Я не понял id чего здесь должно быть.
    // Наверное имеется ввиду, что id в бд когда ссылка там будет,
    // пока реализовано криво, бд добавится в следующей итерации
    Long id;
    URI url;

    public LinkResponse(Long id, URI url) {
        this.id = id;
        this.url = url;
    }
}
