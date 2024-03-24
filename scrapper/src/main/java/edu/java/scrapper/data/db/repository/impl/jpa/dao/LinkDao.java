package edu.java.scrapper.data.db.repository.impl.jpa.dao;

import edu.java.scrapper.data.db.entity.Link;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LinkDao extends JpaRepository<Link, Long> {
    @Query(
            value = "INSERT INTO link (url, last_updated_at) VALUES (:url, :last_updated_at) RETURNING link.id, link.url, link.last_updated_at",
            nativeQuery = true
    )
    Link createAndReturn(@Param("url") String url, @Param("last_updated_at") OffsetDateTime lastUpdatedAt);

    @Query(
            value = "DELETE from link WHERE id=:id AND url=:url AND last_updated_at=:last_updated_at RETURNING link.id, link.url, link.last_updated_at",
            nativeQuery = true
    )
    Optional<Link> deleteAndReturn(@Param("id") Long id, @Param("url") String url, @Param("last_updated_at") OffsetDateTime lastUpdatedAt);


    @Query(
            value = "UPDATE link SET url=:url, last_updated_at=:last_updated_at WHERE id=:id RETURNING link.id, link.url, link.last_updated_at",
            nativeQuery = true
    )
    Optional<Link> updateAndReturn(@Param("id") Long id, @Param("url") String url, @Param("last_updated_at") OffsetDateTime lastUpdatedAt);

    Optional<Link> findLinkByUrl(String url);

    List<Link> findLinksByLastUpdatedAtBefore(OffsetDateTime lastUpdatedAt);
}
