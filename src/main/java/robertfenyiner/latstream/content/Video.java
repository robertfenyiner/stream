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
@Table(name = "videos")
public class Video implements Serializable {

    /**
     * movieCode will be using the FileName
     * so that Android TV browsers can stream
     * videos using Native Media players rather
     * than HTML5 Video for a better UX
     */
    @Id
    private String movieCode;
    
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
    
    // Additional fields for torrent-based content
    private String torrentHash;
    private String downloadUrl;
    private String thumbnailUrl;
}