package edu.java.scrapper.data.network.impl;

import edu.java.core.exception.LinkCannotBeHandledException;
import edu.java.core.util.ApiQualifier;
import edu.java.scrapper.data.db.LinkContentRepository;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.LinkContent;
import edu.java.scrapper.data.network.BaseClient;
import edu.java.scrapper.data.network.StackOverflowConnector;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StackOverflowClientImpl extends BaseClient {
    private final static List<String> URLS = List.of(
            "https://api.stackexchange.com/questions/([^/]+)/answers?site=stackoverflow",
            "https://stackoverflow.com/questions/([^/]+)/([^/]+)"
    );
    private final LinkContentRepository contentRepository;
    private final StackOverflowConnector stackOverflowConnector;


    public StackOverflowClientImpl(
            LinkContentRepository contentRepository,
            StackOverflowConnector stackOverflowConnector
    ) {
        super(URLS);
        this.contentRepository = contentRepository;
        this.stackOverflowConnector = stackOverflowConnector;
    }

    @Override
    public String handle(Link link) throws LinkCannotBeHandledException {
        return "";
    }
}
