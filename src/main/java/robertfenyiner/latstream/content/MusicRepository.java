package com.robertfenyiner.latstream.content;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource(path="music", collectionResourceRel="music")
public interface MusicRepository extends ListCrudRepository<Song, String> {

    boolean existsByContentId(String contentId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Song")
    void bulkDeleteAll();

    // Add to MusicRepository interface
    @Query("SELECT s.contentId FROM Song s")
    List<String> findAllContentIds();
}
