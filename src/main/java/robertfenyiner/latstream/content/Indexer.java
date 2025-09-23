package robertfenyiner.latstream.content;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;
import robertfenyiner.latstream.common.ApplicationConstants;
import robertfenyiner.latstream.common.SOURCE;
import robertfenyiner.latstream.services.ContentDirectoryServices;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@Slf4j
@Service
@RequiredArgsConstructor
public class Indexer {

    private final UnaryOperator<String> decodePathSegment = pathSegment -> 
        UriUtils.decode(pathSegment, StandardCharsets.UTF_8.name());
    
    private final Function<Path, String> decodeContentType = fileEntryPath -> 
        MediaTypeFactory.getMediaType(new FileSystemResource(fileEntryPath))
            .orElse(MediaType.APPLICATION_OCTET_STREAM).toString();
    
    private final ContentDirectoryServices contentDirectoryServices;
    private final VideoRepository videoRepository;
    private final MusicRepository musicRepository;

    /**
     * Concurrent indexer for local media
     */
    public CompletableFuture<Void> indexLocalMedia(Set<String> locations) {
        return findLocalMediaFiles(locations)
                .thenCompose(paths -> {
                    List<Path> musicPaths = filterPaths(paths, ApplicationConstants.AUDIO_EXTENSIONS);
                    List<Path> videoFolderPaths = filterPaths(paths, ApplicationConstants.VIDEO_EXTENSIONS);

                    try {
                        List<Video> finalVideos = createVideoEntities(videoFolderPaths);
                        List<Song> finalSongs = createMusicEntities(musicPaths);

                        CompletableFuture<Void> videosFuture = saveVideosAsync(finalVideos);
                        CompletableFuture<Void> musicFuture = saveMusicAsync(finalSongs);

                        return CompletableFuture.allOf(videosFuture, musicFuture);
                    } catch (IOException e) {
                        log.error("Error creating media entities", e);
                        return CompletableFuture.failedFuture(e);
                    }
                })
                .exceptionally(throwable -> {
                    log.error("Error during media indexing", throwable);
                    return null;
                });
    }

    public CompletableFuture<List<Path>> findLocalMediaFiles(Set<String> locations) {
        return CompletableFuture.supplyAsync(() -> {
            List<Path> allPaths = new ArrayList<>();
            
            for (String location : locations) {
                try {
                    Path startPath = Paths.get(location);
                    if (!Files.exists(startPath)) {
                        log.warn("Path does not exist: {}", location);
                        continue;
                    }
                    
                    Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                            if (attrs.isRegularFile()) {
                                allPaths.add(file);
                            }
                            return FileVisitResult.CONTINUE;
                        }
                        
                        @Override
                        public FileVisitResult visitFileFailed(Path file, IOException exc) {
                            log.warn("Failed to visit file: {}", file, exc);
                            return FileVisitResult.CONTINUE;
                        }
                    });
                } catch (IOException e) {
                    log.error("Error walking file tree for location: {}", location, e);
                }
            }
            
            log.info("Found {} total files", allPaths.size());
            return allPaths;
        });
    }

    private List<Path> filterPaths(List<Path> paths, String... extensions) {
        Set<String> extensionSet = Set.of(extensions);
        return paths.stream()
                .filter(path -> {
                    String fileName = path.getFileName().toString().toLowerCase();
                    return extensionSet.stream().anyMatch(fileName::endsWith);
                })
                .toList();
    }

    private List<Video> createVideoEntities(List<Path> paths) throws IOException {
        Path userHomePath = Paths.get(ContentDirectoryServices.userHomePath);

        return paths.parallelStream().map(entry -> {
            try {
                String encodedFileName = decodePathSegment.apply(entry.getFileName().toString());
                Path relativePath = userHomePath.relativize(entry);

                return new Video()
                        .setName(encodedFileName)
                        .setContentLength(Files.size(entry))
                        .setSummary(entry.getFileName().toString())
                        .setContentId(File.separator + decodePathSegment.apply(relativePath.toString()))
                        .setContentMimeType(decodeContentType.apply(entry))
                        .setMovieCode(encodedFileName)
                        .setSource(SOURCE.LOCAL);
            } catch (IOException e) {
                log.error("Error creating video entity for {}", entry, e);
                return null;
            }
        }).filter(video -> video != null).toList();
    }

    private CompletableFuture<Void> saveVideosAsync(List<Video> videos) {
        return CompletableFuture.runAsync(() -> {
            try {
                videoRepository.saveAll(videos);
                log.info("Saved {} videos to database", videos.size());
            } catch (Exception e) {
                log.error("Error saving videos to database", e);
            }
        });
    }

    private CompletableFuture<Void> saveMusicAsync(List<Song> songs) {
        return CompletableFuture.runAsync(() -> {
            try {
                musicRepository.saveAll(songs);
                log.info("Saved {} songs to database", songs.size());
            } catch (Exception e) {
                log.error("Error saving songs to database", e);
            }
        });
    }

    private List<Song> createMusicEntities(List<Path> paths) throws IOException {
        List<Song> songs = new ArrayList<>();
        Path userHomePath = Paths.get(ContentDirectoryServices.userHomePath);

        for (Path entry : paths) {
            try {
                String encodedFileName = decodePathSegment.apply(entry.getFileName().toString());
                Path relativePath = userHomePath.relativize(entry);

                Song song = new Song()
                        .setName(encodedFileName)
                        .setContentLength(Files.size(entry))
                        .setSummary(entry.getFileName().toString())
                        .setContentId(File.separator + decodePathSegment.apply(relativePath.toString()))
                        .setContentMimeType(decodeContentType.apply(entry))
                        .setSongId(encodedFileName)
                        .setSource(SOURCE.LOCAL);

                songs.add(song);
            } catch (IOException e) {
                log.error("Error creating song entity for {}", entry, e);
            }
        }

        return songs;
    }

    /**
     * Index a downloaded torrent file as video content
     */
    public void indexTorrentVideo(String fileName, String torrentHash, String downloadPath) {
        try {
            Path filePath = Paths.get(downloadPath, fileName);
            if (!Files.exists(filePath)) {
                log.warn("Torrent file does not exist: {}", filePath);
                return;
            }

            Video video = new Video()
                    .setName(fileName)
                    .setMovieCode(fileName)
                    .setContentLength(Files.size(filePath))
                    .setSummary("Downloaded from LAT-Team torrent")
                    .setContentId(downloadPath + File.separator + fileName)
                    .setContentMimeType(decodeContentType.apply(filePath))
                    .setTorrentHash(torrentHash)
                    .setSource(SOURCE.TORRENT);

            videoRepository.save(video);
            log.info("Indexed torrent video: {}", fileName);
        } catch (IOException e) {
            log.error("Error indexing torrent video: {}", fileName, e);
        }
    }
}