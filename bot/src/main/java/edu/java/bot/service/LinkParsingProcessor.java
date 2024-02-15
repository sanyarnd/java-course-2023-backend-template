package edu.java.bot.service;

import edu.java.bot.parser.LinkParser;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkParsingProcessor {

    private final List<LinkParser> parsers;

    public boolean processParsing(URI uri) {
        for (var parser: parsers) {
            if (parser.parseLink(uri)) {
                return true;
            }
        }
        return false;
    }
}
