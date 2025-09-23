package robertfenyiner.latstream.content;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;
import org.springframework.content.commons.annotations.MimeType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import robertfenyiner.latstream.common.SOURCE;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "songs")
public class Song implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String songId;
    
    private String name;
    
    @CreatedDate
    private LocalDateTime created;
    
    private String summary;
    
    @ContentId
    private String contentId;
    
    @ContentLength
    private long contentLength;
    
    @MimeType
    private String contentMimeType;
    
    @Enumerated(EnumType.STRING)
    private SOURCE source;
    
    // Audio-specific fields
    private String artist;
    private String album;
    private Integer duration; // in seconds
}