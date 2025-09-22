package robertfenyiner.latstream.www;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import robertfenyiner.latstream.config.UNIT3DConfig;
import robertfenyiner.latstream.services.LatTeamSearchService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private UNIT3DConfig unit3dConfig;
    
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
        log.info("Redirigiendo a LAT-Team para torrent ID: {}", id);
        
        try {
            // Redirect directly to LAT-Team torrent page
            String torrentUrl = unit3dConfig.getBaseUrl() + "/torrents/" + id;
            return "redirect:" + torrentUrl;
            
        } catch (Exception e) {
            log.error("Error redirecting to torrent ID: " + id, e);
            // Fallback to home page
            return "redirect:/";
        }
    }
}