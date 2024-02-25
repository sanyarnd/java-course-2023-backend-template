package edu.java.dto.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class Answer {

    @JsonSetter("answer_id")
    Long id;
    @JsonSetter("question_id")
    Long questionId;
    @JsonSetter("last_activity_date")
    OffsetDateTime activityDate;
    @JsonProperty
    String link;
    Owner owner;
}
