package edu.java.bot.clients.dto;

import java.util.List;
import lombok.Data;

@Data
public class ListLinkResponse {
    List<LinkResponse> links;
    Integer size;
}
