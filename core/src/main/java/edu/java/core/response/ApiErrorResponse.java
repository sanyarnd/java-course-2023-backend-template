package edu.java.core.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record ApiErrorResponse(
    @JsonProperty("description")
    String description,

    @JsonProperty("code")
    Integer code,

    @JsonProperty("exceptionName")
    String exceptionName,

    @JsonProperty("exceptionMessage")
    String exceptionMessage,

    @JsonProperty("stacktrace")
    List<String> stacktrace
) {
}
