package edu.java.scrapper.data.db.repository.impl.jpa;

import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.repository.LinkRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;

public class JpaLinkRepositoryImpl implements LinkRepository {
    @Override
    public Optional<Link> get(Long entityId) {
        return Optional.empty();
    }

    @Override
    public List<Link> getAll() {
        return null;
    }

    @Override
    public Link createAndReturn(Link entity) throws DataIntegrityViolationException {
        return null;
    }

    @Override
    public Optional<Link> deleteAndReturn(Link entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Link> updateAndReturn(Link entity) {
        return Optional.empty();
    }

    @Override
    public Link upsertAndReturn(Link entity) {
        return null;
    }

    @Override
    public Optional<Link> getByUrl(String url) {
        return Optional.empty();
    }

    @Override
    public List<Link> getAllUpdatedBefore(OffsetDateTime timeBefore) {
        return null;
    }
}
