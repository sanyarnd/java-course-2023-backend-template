package edu.java.api.scrapper.dto.response;

import java.util.List;

public record ListLinksResponse(List<LinkResponse> linkResponseList, int size) {
}
