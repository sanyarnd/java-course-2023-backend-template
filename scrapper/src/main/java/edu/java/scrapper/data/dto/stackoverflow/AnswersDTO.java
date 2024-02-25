package edu.java.scrapper.data.dto.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record AnswersDTO(
    @JsonProperty("items")
    List<AnswerDTO> answers
) {
}
