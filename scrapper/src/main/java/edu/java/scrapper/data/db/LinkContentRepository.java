package edu.java.scrapper.data.db;

import edu.java.scrapper.data.db.entity.LinkContent;

public interface LinkContentRepository extends CrudRepository<LinkContent, Long> {
    void updateContent(LinkContent linkContent);
}
