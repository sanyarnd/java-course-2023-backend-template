package edu.java.scrapper.data.dto.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AnswerOwnerDTO(
	@JsonProperty("account_id")
	int accountId,

	@JsonProperty("user_type")
	String userType,

	@JsonProperty("user_id")
	int userId,

	@JsonProperty("reputation")
	int reputation,

	@JsonProperty("display_name")
	String displayName
) {
}
