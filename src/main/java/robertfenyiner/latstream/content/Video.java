package robertfenyiner.latstream.content;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;
import org.springframework.content.commons.annotations.MimeType;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class Video implements Serializable {

    /**
     * movieCode will be using the torrent ID/hash
     * so that we can stream videos directly from torrents
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
    
    // LAT-Team specific fields
    private String torrentId;
    private String torrentHash;
    private String latTeamUrl;
}