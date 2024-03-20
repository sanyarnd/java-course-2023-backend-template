package edu.java.scrapper.data.db;

import edu.java.core.util.BaseRepository;
import edu.java.scrapper.data.db.entity.LinkContent;

public interface LinkContentRepository extends BaseRepository<LinkContent, Long> {
    void updateContent(LinkContent linkContent);
}
