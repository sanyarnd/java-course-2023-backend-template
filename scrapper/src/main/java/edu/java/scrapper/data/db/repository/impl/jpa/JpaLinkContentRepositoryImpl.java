package edu.java.scrapper.data.db.repository.impl.jpa;

import edu.java.scrapper.data.db.entity.LinkContent;
import edu.java.scrapper.data.db.repository.LinkContentRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;

public class JpaLinkContentRepositoryImpl implements LinkContentRepository {
    @Override
    public Optional<LinkContent> get(Long entityId) {
        return Optional.empty();
    }

    @Override
    public List<LinkContent> getAll() {
        return null;
    }

    @Override
    public void create(LinkContent entity) throws DataIntegrityViolationException {

    }

    @Override
    public void delete(LinkContent entity) {

    }

    @Override
    public void update(LinkContent entity) {

    }

    @Override
    public void upsert(LinkContent entity) {

    }
}
