package robertfenyiner.latstream.services;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import robertfenyiner.latstream.common.ApplicationConstants;
import robertfenyiner.latstream.common.CONTENTTYPE;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
@Setter
@Service
public class ContentDirectoryServices {

    public static final Map<CONTENTTYPE, String> mediaFolders = new HashMap<>(3);
    public static final String userHomePath = System.getProperty("user.home");
    
    private String downloadsFolderPath;
    private String musicFolderPath;
    private String moviesFolderPath;
    private String musicContentStore;
    private String moviesContentStore;

    public ContentDirectoryServices() {
        String os = System.getProperty("os.name").toLowerCase();
        log.info("OS: {}", os);

        if (os.contains("win")) {
            downloadsFolderPath = userHomePath + File.separator + ApplicationConstants.DOWNLOADS;
            musicFolderPath = userHomePath + File.separator + ApplicationConstants.MUSIC;
            moviesFolderPath = userHomePath + File.separator + ApplicationConstants.VIDEOS;
            musicContentStore = ApplicationConstants.MUSIC + File.separator;
            moviesContentStore = ApplicationConstants.VIDEOS + File.separator;
        } else if (os.contains("mac") || os.contains("nix") || os.contains("nux") || os.contains("bsd")) {
            downloadsFolderPath = userHomePath + File.separator + ApplicationConstants.DOWNLOADS;
            musicFolderPath = userHomePath + File.separator + ApplicationConstants.MUSIC;
            moviesFolderPath = userHomePath + File.separator + "Movies";
            musicContentStore = ApplicationConstants.MUSIC + File.separator;
            moviesContentStore = "Movies" + File.separator;
        } else {
            log.warn("Unknown OS file folder structure! Defaulting to user.home...");
            downloadsFolderPath = userHomePath;
            musicFolderPath = userHomePath;
            moviesFolderPath = userHomePath;
            musicContentStore = userHomePath;
            moviesContentStore = userHomePath;
        }

        log.info("Final locations paths:");
        log.info("Downloads: {}", downloadsFolderPath);
        log.info("Music: {}", musicContentStore);
        log.info("Movies: {}", moviesContentStore);
        
        mediaFolders.put(CONTENTTYPE.VIDEO, moviesFolderPath);
        mediaFolders.put(CONTENTTYPE.AUDIO, musicFolderPath);
        mediaFolders.put(CONTENTTYPE.OTHER, downloadsFolderPath);
        
        // Create directories if they don't exist
        createDirectories();
    }
    
    private void createDirectories() {
        createDirectory(downloadsFolderPath);
        createDirectory(musicFolderPath);
        createDirectory(moviesFolderPath);
    }
    
    private void createDirectory(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (created) {
                log.info("Created directory: {}", path);
            } else {
                log.warn("Failed to create directory: {}", path);
            }
        }
    }
}