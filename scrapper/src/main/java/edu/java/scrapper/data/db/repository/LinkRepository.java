package edu.java.scrapper.data.db.repository;

import edu.java.scrapper.data.db.ReadRepository;
import edu.java.scrapper.data.db.WriteAndReturnRepository;
import edu.java.scrapper.data.db.entity.Link;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface LinkRepository extends ReadRepository<Link, Long>, WriteAndReturnRepository<Link> {
    Optional<Link> getByUrl(String url);

    List<Link> getAllUpdatedBefore(OffsetDateTime timeBefore);
}
