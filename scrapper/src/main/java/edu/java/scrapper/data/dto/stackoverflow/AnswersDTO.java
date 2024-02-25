package edu.java.scrapper.data.dto.stackoverflow;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public record AnswersDTO(
	@JsonProperty("items")
	List<AnswerDTO> answers
) {
}
