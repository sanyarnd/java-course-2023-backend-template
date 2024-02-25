package edu.java.dto.github;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@Data
public class Owner {

    @JsonSetter("id")
    long id;
    @JsonSetter("login")
    String login;
}
