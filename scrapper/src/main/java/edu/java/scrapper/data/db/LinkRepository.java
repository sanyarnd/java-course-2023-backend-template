package edu.java.scrapper.data.db;

import edu.java.core.util.BaseRepository;
import edu.java.scrapper.data.db.entity.Link;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
public interface LinkRepository extends BaseRepository<Link, Long> {
    Link addOrGetExisted(Link link);

    void updateTimeTouched(Link link);

    List<Link> findAllLinksUpdatedBefore(OffsetDateTime offsetDateTime);

    Optional<Link> findByUrl(String url);
}
