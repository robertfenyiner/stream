package robertfenyiner.latstream.content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import robertfenyiner.latstream.common.SOURCE;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {
    
    List<Video> findBySource(SOURCE source);
    
    List<Video> findByNameContainingIgnoreCase(String name);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Video")
    void bulkDeleteAll();
    
    @Query("SELECT COUNT(v) FROM Video v WHERE v.source = :source")
    long countBySource(SOURCE source);
}