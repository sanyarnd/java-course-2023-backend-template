package edu.java.payload.dto.response;

import java.util.List;

public record ListLinksResponse(List<LinkResponse> linkResponseList, int size) {
}
