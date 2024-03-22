package edu.java.core.response.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record CommentResponse(
        @JsonProperty("items")
        List<Comment> comments
) {
}
