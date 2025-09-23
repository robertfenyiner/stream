package robertfenyiner.latstream.content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import robertfenyiner.latstream.common.SOURCE;

import java.util.List;
import java.util.Optional;

@Repository
public interface MusicRepository extends JpaRepository<Song, Long> {
    
    Optional<Song> findBySongId(String songId);
    
    List<Song> findBySource(SOURCE source);
    
    List<Song> findByNameContainingIgnoreCase(String name);
    
    List<Song> findByArtistContainingIgnoreCase(String artist);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Song")
    void bulkDeleteAll();
    
    @Query("SELECT COUNT(s) FROM Song s WHERE s.source = :source")
    long countBySource(SOURCE source);
}