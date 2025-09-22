package com.robertfenyiner.latstream.www;

import com.robertfenyiner.latstream.preferences.Preference;
import com.robertfenyiner.latstream.preferences.UserPreferences;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 * Unified Search Controller that can use either YTS (public) or UNIT3D (private) APIs
 */
@Slf4j
@Controller
@RequestMapping("/search")
@RequiredArgsConstructor
public class UnifiedSearchController {

    private final YTSAPIClient ytsapiClient;
    private final Optional<UNIT3DAPIClient> unit3dAPIClient;
    private final UserPreferences userPreferences;

    @Value("${unit3d.api.enabled:false}")
    private boolean unit3dEnabled;

    @GetMapping("/torrents")
    public String searchTorrents(@RequestParam("term") String term, Model model) {
        Optional<Preference> darkModePreference = userPreferences.findById(1);
        boolean darkModeEnabled = darkModePreference.map(Preference::isEnabled).orElse(false);
        model.addAttribute("darkmodeenabled", darkModeEnabled);
        
        if (term == null || term.trim().isEmpty()) {
            return "";
        }

        try {
            if (unit3dEnabled && unit3dAPIClient.isPresent()) {
                // Use UNIT3D private tracker
                log.info("Searching private tracker (UNIT3D) for: {}", term);
                var results = unit3dAPIClient.get().searchTorrents(term, 1, 50);
                model.addAttribute("unit3dResults", results.data());
                model.addAttribute("searchProvider", "UNIT3D");
            } else {
                // Fallback to YTS public tracker
                log.info("Searching YTS public tracker for: {}", term);
                var results = ytsapiClient.ytsSearchV2(term);
                model.addAttribute("results", results.data());
                model.addAttribute("searchProvider", "YTS");
            }
        } catch (Exception e) {
            log.error("Error searching for torrents: {}", e.getMessage());
            model.addAttribute("searchError", "Error searching for torrents: " + e.getMessage());
        }

        return "index";
    }

    @HxRequest
    @GetMapping("/torrents")
    public String searchTorrentsAsync(@RequestParam("term") String term, Model model) {
        Optional<Preference> darkModePreference = userPreferences.findById(1);
        boolean darkModeEnabled = darkModePreference.map(Preference::isEnabled).orElse(false);
        model.addAttribute("darkmodeenabled", darkModeEnabled);
        
        if (term == null || term.trim().isEmpty()) {
            return "";
        }

        try {
            if (unit3dEnabled && unit3dAPIClient.isPresent()) {
                // Use UNIT3D private tracker
                log.info("Async searching private tracker (UNIT3D) for: {}", term);
                var results = unit3dAPIClient.get().searchTorrents(term, 1, 50);
                model.addAttribute("unit3dResults", results.data());
                model.addAttribute("searchProvider", "UNIT3D");
                return "index :: unit3d-search-results";
            } else {
                // Fallback to YTS public tracker
                log.info("Async searching YTS public tracker for: {}", term);
                var results = ytsapiClient.ytsSearchV2(term);
                model.addAttribute("results", results.data());
                model.addAttribute("searchProvider", "YTS");
                return "index :: search-results";
            }
        } catch (Exception e) {
            log.error("Error searching for torrents: {}", e.getMessage());
            model.addAttribute("searchError", "Error searching for torrents: " + e.getMessage());
            return "index :: search-error";
        }
    }

    // Note: Legacy YTS endpoints are handled by SearchController to avoid conflicts

    // UNIT3D specific endpoints
    @GetMapping("/unit3d/latest")
    public String getLatestTorrents(Model model) {
        if (!unit3dEnabled || unit3dAPIClient.isEmpty()) {
            model.addAttribute("error", "UNIT3D API is not enabled");
            return "error";
        }

        try {
            var results = unit3dAPIClient.get().getLatestTorrents(1, 20);
            model.addAttribute("unit3dResults", results.data());
            model.addAttribute("searchProvider", "UNIT3D");
            return "index :: unit3d-search-results";
        } catch (Exception e) {
            log.error("Error getting latest torrents: {}", e.getMessage());
            model.addAttribute("searchError", "Error getting latest torrents");
            return "index :: search-error";
        }
    }

    @GetMapping("/unit3d/freeleech")
    public String getFreeleechTorrents(Model model) {
        if (!unit3dEnabled || unit3dAPIClient.isEmpty()) {
            model.addAttribute("error", "UNIT3D API is not enabled");
            return "error";
        }

        try {
            var results = unit3dAPIClient.get().getFreeleechTorrents(1, 20);
            model.addAttribute("unit3dResults", results.data());
            model.addAttribute("searchProvider", "UNIT3D");
            return "index :: unit3d-search-results";
        } catch (Exception e) {
            log.error("Error getting freeleech torrents: {}", e.getMessage());
            model.addAttribute("searchError", "Error getting freeleech torrents");
            return "index :: search-error";
        }
    }
}
