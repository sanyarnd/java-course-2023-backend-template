package edu.java.scrapper.data.db.repository.impl.jpa;

import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.repository.LinkRepository;
import edu.java.scrapper.data.db.repository.impl.jpa.dao.LinkDao;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

@AllArgsConstructor
public class JpaLinkRepositoryImpl implements LinkRepository {
    private final LinkDao linkDao;

    @Override
    public Optional<Link> get(Long entityId) {
        return linkDao.findById(entityId);
    }

    @Override
    public List<Link> getAll() {
        return linkDao.findAll();
    }

    @Override
    public Link createAndReturn(Link entity) throws DataIntegrityViolationException {
        return linkDao.createAndReturn(entity.getUrl(), entity.getLastUpdatedAt());
    }

    @Override
    public Optional<Link> deleteAndReturn(Link entity) {
        return linkDao.deleteAndReturn(entity.getId(), entity.getUrl(), entity.getLastUpdatedAt());
    }

    @Override
    public Optional<Link> updateAndReturn(Link entity) {
        return linkDao.updateAndReturn(entity.getId(), entity.getUrl(), entity.getLastUpdatedAt());
    }

    @Override
    public Link upsertAndReturn(Link entity) {
        return linkDao.save(entity);
    }

    @Override
    public Optional<Link> getByUrl(String url) {
        return linkDao.findLinkByUrl(url);
    }

    @Override
    public List<Link> getAllUpdatedBefore(OffsetDateTime timeBefore) {
        return linkDao.findLinksByLastUpdatedAtBefore(timeBefore);
    }
}
