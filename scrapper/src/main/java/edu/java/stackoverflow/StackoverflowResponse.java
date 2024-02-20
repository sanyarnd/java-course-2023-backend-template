package edu.java.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record StackoverflowResponse(@JsonProperty("items") List<StackoverflowQuestion> items) {
}
