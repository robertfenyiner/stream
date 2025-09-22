package robertfenyiner.latstream.config;

import org.springframework.content.fs.config.EnableFilesystemStores;
import org.springframework.content.fs.io.FileSystemResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Paths;

@Configuration
@EnableFilesystemStores
public class SpringContentStorageConfig {

    @Bean
    FileSystemResourceLoader fileSystemResourceLoader() {
        // Create media directory if it doesn't exist
        String mediaPath = System.getProperty("user.home") + File.separator + "LatStream" + File.separator + "media";
        File mediaDir = new File(mediaPath);
        if (!mediaDir.exists()) {
            mediaDir.mkdirs();
        }
        
        return new FileSystemResourceLoader(Paths.get(mediaPath).toString());
    }

}