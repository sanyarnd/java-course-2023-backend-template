package edu.java.dto.stackoverflow;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@Data
public class Owner {

    @JsonSetter("user_id")
    long id;
    @JsonSetter("display_name")
    String name;
}
