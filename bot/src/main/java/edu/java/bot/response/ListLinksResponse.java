package edu.java.bot.response;

import java.util.List;

public record ListLinksResponse(List<LinkResponse> linkResponseList, int size) {
}
