package edu.java.scrapper.data.db.repository.impl.jpa.dao;

import edu.java.scrapper.data.db.entity.LinkContent;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LinkContentDao extends JpaRepository<LinkContent, Long> {
    @Modifying(clearAutomatically = true)
    @Query(
            value = "INSERT INTO link_content (id, raw, hash) VALUES (:id, :raw, :hash)",
            nativeQuery = true
    )
    int create(@Param("id") Long linkId, @Param("raw") String rawContent, @Param("hash") Integer hash)
            throws DataIntegrityViolationException;

    @Modifying(clearAutomatically = true)
    @Query(
            value = "DELETE from link_content WHERE id=:id AND raw=:raw AND hash=:hash",
            nativeQuery = true
    )
    int delete(@Param("id") Long linkId, @Param("raw") String rawContent, @Param("hash") Integer hash);

    @Modifying(clearAutomatically = true)
    @Query(
            value = "UPDATE link_content SET raw=:raw, hash=:hash WHERE id=:id",
            nativeQuery = true
    )
    int update(@Param("id") Long linkId, @Param("raw") String rawContent, @Param("hash") Integer hash);
}
