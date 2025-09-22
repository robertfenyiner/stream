package robertfenyiner.latstream.www;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import robertfenyiner.latstream.services.LatTeamSearchService;
import robertfenyiner.latstream.models.TorrentResult;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Controller
public class HomeController {

    @Autowired
    private LatTeamSearchService latTeamSearchService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("darkmodeenabled", false);
        model.addAttribute("title", "LatStream - LAT-Team Private Tracker Interface");
        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        model.addAttribute("darkmodeenabled", false);
        model.addAttribute("title", "LatStream - Resultados de búsqueda");
        
        // Buscar en LAT-Team
        List<TorrentResult> torrents = latTeamSearchService.searchTorrents(query);
        
        // Crear estructura de resultados
        Map<String, Object> results = new HashMap<>();
        results.put("torrents", torrents);
        
        model.addAttribute("results", results);
        model.addAttribute("searchQuery", query);
        
        return "index";
    }
}