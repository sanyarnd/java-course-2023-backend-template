package edu.java.scrapper.data.dto.stackoverflow;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public record StackOverflowQuestionAnswers(
	@JsonProperty("quota_max")
	int quotaMax,

	@JsonProperty("quota_remaining")
	int quotaRemaining,

	@JsonProperty("has_more")
	boolean hasMore,

	@JsonProperty("items")
	List<Answer> answers
) {
}
