package robertfenyiner.latstream.torrent;

import robertfenyiner.latstream.content.Video;
import robertfenyiner.latstream.content.VideoRepository;
import robertfenyiner.latstream.content.SOURCE;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class TorrentStreamService {

    private final VideoRepository videoRepository;

    public void startTorrentStream(String torrentId, String torrentHash, String torrentName, String latTeamUrl) {
        log.info("Starting torrent stream for: {} ({})", torrentName, torrentId);

        // Check if video already exists
        if (!videoRepository.existsByTorrentId(torrentId)) {
            // Create video entity for streaming
            Video video = new Video()
                    .setMovieCode(torrentId)
                    .setName(torrentName)
                    .setCreated(LocalDateTime.now())
                    .setSummary("Streaming desde LAT-Team: " + torrentName)
                    .setContentMimeType("video/mp4") // Default mime type
                    .setSource(SOURCE.TORRENT)
                    .setTorrentId(torrentId)
                    .setTorrentHash(torrentHash)
                    .setLatTeamUrl(latTeamUrl);

            videoRepository.save(video);
            log.info("Video entity created for streaming: {}", torrentName);
        } else {
            log.info("Video already exists in repository: {}", torrentName);
        }
    }

    public boolean isVideoReady(String torrentId) {
        return videoRepository.existsByTorrentId(torrentId);
    }

}