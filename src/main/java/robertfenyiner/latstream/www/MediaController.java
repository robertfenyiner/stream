package robertfenyiner.latstream.www;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import robertfenyiner.latstream.config.UNIT3DConfig;

@Controller
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private UNIT3DConfig unit3dConfig;

    @GetMapping("/download/{id}")
    public String downloadTorrent(@PathVariable String id) {
        // Redirigir a la URL de descarga de LAT-Team
        String downloadUrl = unit3dConfig.getBaseUrl() + "/torrents/download/" + id;
        return "redirect:" + downloadUrl;
    }

    @GetMapping("/stream/{id}")
    public String streamTorrent(@PathVariable String id) {
        // Por ahora redirigir a la página de torrent en LAT-Team
        // En el futuro se puede implementar streaming directo
        String torrentUrl = unit3dConfig.getBaseUrl() + "/torrents/" + id;
        return "redirect:" + torrentUrl;
    }
}