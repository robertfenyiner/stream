package com.akshathsaipittala.streamspace.www;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller specifically for UNIT3D private tracker functionality
 */
@Slf4j
@Controller
@RequestMapping("/unit3d")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "unit3d.api.enabled", havingValue = "true")
public class UNIT3DController {

    private final UNIT3DAPIClient unit3dAPIClient;

    @GetMapping("/torrents/{id}")
    public String getTorrentDetails(@PathVariable int id, Model model) {
        try {
            var torrent = unit3dAPIClient.getTorrentDetails(id);
            model.addAttribute("torrent", torrent.data());
            return "unit3d :: torrentDetails";
        } catch (Exception e) {
            log.error("Error getting torrent details for ID {}: {}", id, e.getMessage());
            model.addAttribute("error", "Error loading torrent details");
            return "error";
        }
    }

    @GetMapping("/browse")
    public String browseTorrents(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "25") int perPage,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer typeId,
            @RequestParam(required = false) Integer resolutionId,
            Model model) {
        
        try {
            var results = search != null && !search.trim().isEmpty() 
                ? unit3dAPIClient.filterTorrents(search, categoryId, typeId, resolutionId, page, perPage)
                : unit3dAPIClient.getTorrents(page, perPage);
                
            model.addAttribute("torrents", results.data());
            model.addAttribute("pagination", results.meta());
            model.addAttribute("currentSearch", search);
            model.addAttribute("currentCategoryId", categoryId);
            model.addAttribute("currentTypeId", typeId);
            model.addAttribute("currentResolutionId", resolutionId);
            
            return "unit3d :: browseTorrents";
        } catch (Exception e) {
            log.error("Error browsing torrents: {}", e.getMessage());
            model.addAttribute("error", "Error loading torrents");
            return "error";
        }
    }

    @GetMapping("/movies")
    public String getMovies(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "25") int perPage,
            Model model) {
        
        try {
            // Category ID 1 is typically Movies in UNIT3D, adjust as needed for your tracker
            var results = unit3dAPIClient.filterTorrents("", 1, null, null, page, perPage);
            model.addAttribute("movies", results.data());
            model.addAttribute("pagination", results.meta());
            return "unit3d :: movies";
        } catch (Exception e) {
            log.error("Error getting movies: {}", e.getMessage());
            model.addAttribute("error", "Error loading movies");
            return "error";
        }
    }

    @GetMapping("/tv")
    public String getTVShows(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "25") int perPage,
            Model model) {
        
        try {
            // Category ID 2 is typically TV Shows in UNIT3D, adjust as needed for your tracker
            var results = unit3dAPIClient.filterTorrents("", 2, null, null, page, perPage);
            model.addAttribute("tvShows", results.data());
            model.addAttribute("pagination", results.meta());
            return "unit3d :: tvShows";
        } catch (Exception e) {
            log.error("Error getting TV shows: {}", e.getMessage());
            model.addAttribute("error", "Error loading TV shows");
            return "error";
        }
    }

    @GetMapping("/top-seeded")
    public String getTopSeeded(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "25") int perPage,
            Model model) {
        
        try {
            var results = unit3dAPIClient.getMostSeededTorrents(page, perPage);
            model.addAttribute("topSeeded", results.data());
            model.addAttribute("pagination", results.meta());
            return "unit3d :: topSeeded";
        } catch (Exception e) {
            log.error("Error getting top seeded torrents: {}", e.getMessage());
            model.addAttribute("error", "Error loading top seeded torrents");
            return "error";
        }
    }
}