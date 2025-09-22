package robertfenyiner.latstream.www;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import robertfenyiner.latstream.config.UNIT3DConfig;
import robertfenyiner.latstream.torrent.TorrentStreamService;
import robertfenyiner.latstream.services.LatTeamSearchService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private UNIT3DConfig unit3dConfig;
    
    @Autowired
    private TorrentStreamService torrentStreamService;
    
    @Autowired
    private LatTeamSearchService latTeamSearchService;

    @GetMapping("/download/{id}")
    public String downloadTorrent(@PathVariable String id) {
        // Redirigir a la URL de descarga de LAT-Team
        String downloadUrl = unit3dConfig.getBaseUrl() + "/torrents/download/" + id;
        return "redirect:" + downloadUrl;
    }

    @GetMapping("/stream/{id}")
    public String streamTorrent(@PathVariable String id, Model model) {
        log.info("Iniciando stream para torrent ID: {}", id);
        
        try {
            // Get torrent details from LAT-Team API if needed
            String torrentName = "LAT-Team Torrent " + id; // You can enhance this by fetching actual details
            String torrentHash = id; // LAT-Team often uses ID as hash, but you might need to fetch this
            String latTeamUrl = unit3dConfig.getBaseUrl() + "/torrents/" + id;
            
            // Initialize torrent streaming
            torrentStreamService.startTorrentStream(id, torrentHash, torrentName, latTeamUrl);
            
            // Check if video is ready for streaming
            if (torrentStreamService.isVideoReady(id)) {
                // Redirect to stream player
                return "redirect:/stream/video/" + id;
            } else {
                // Show loading/download progress page
                model.addAttribute("torrentId", id);
                model.addAttribute("torrentName", torrentName);
                model.addAttribute("latTeamUrl", latTeamUrl);
                return "streaming-loading";
            }
            
        } catch (Exception e) {
            log.error("Error starting torrent stream for ID: " + id, e);
            // Fallback to LAT-Team redirect
            String torrentUrl = unit3dConfig.getBaseUrl() + "/torrents/" + id;
            return "redirect:" + torrentUrl;
        }
    }
}