package edu.java.scrapper.data.db.repository.impl.jpa;

import edu.java.scrapper.data.db.entity.LinkContent;
import edu.java.scrapper.data.db.repository.LinkContentRepository;
import edu.java.scrapper.data.db.repository.impl.jpa.dao.LinkContentDao;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

@AllArgsConstructor
public class JpaLinkContentRepositoryImpl implements LinkContentRepository {
    private final LinkContentDao linkContentDao;

    @Override
    public Optional<LinkContent> get(Long entityId) {
        return linkContentDao.findById(entityId);
    }

    @Override
    public List<LinkContent> getAll() {
        return linkContentDao.findAll();
    }

    @Override
    public void create(LinkContent entity) throws DataIntegrityViolationException {
        linkContentDao.create(entity.getId(), entity.getRaw(), entity.getHash());
    }

    @Override
    public void delete(LinkContent entity) {
        linkContentDao.delete(entity.getId(), entity.getRaw(), entity.getHash());
    }

    @Override
    public void update(LinkContent entity) {
        linkContentDao.update(entity.getId(), entity.getRaw(), entity.getHash());
    }

    @Override
    public void upsert(LinkContent entity) {
        linkContentDao.save(entity);
    }
}
