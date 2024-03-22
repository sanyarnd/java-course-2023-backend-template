package edu.java.scrapper.data.db.repository;

import edu.java.scrapper.data.db.ReadRepository;
import edu.java.scrapper.data.db.WriteRepository;
import edu.java.scrapper.data.db.entity.LinkContent;

public interface LinkContentRepository extends ReadRepository<LinkContent, Long>, WriteRepository<LinkContent> {
}
