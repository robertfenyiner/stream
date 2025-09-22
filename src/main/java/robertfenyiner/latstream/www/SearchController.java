package com.robertfenyiner.latstream.www;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Legacy search controller - redirects to unified search
 * Maintained for backward compatibility
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    @GetMapping("/yts")
    public String legacySearch(@RequestParam("term") String term) {
        // Redirect legacy YTS searches to LAT-Team search
        return "redirect:/search/torrents?term=" + term;
    }
}
