package robertfenyiner.latstream.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import robertfenyiner.latstream.content.Indexer;

import java.util.HashSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackgroundServices {

    private final Indexer indexer;
    private final ContentDirectoryServices contentDirectoryServices;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReadyEvent() {
        log.info("LatStream started - Indexing Local Media");

        // Index local media asynchronously
        indexer.indexLocalMedia(new HashSet<>(ContentDirectoryServices.mediaFolders.values()))
                .thenRun(() -> log.info("Media indexing completed"))
                .exceptionally(throwable -> {
                    log.error("Error during media indexing", throwable);
                    return null;
                });
    }
}