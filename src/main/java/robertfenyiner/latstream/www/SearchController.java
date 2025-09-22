package robertfenyiner.latstream.www;package com.robertfenyiner.latstream.www;



import lombok.extern.slf4j.Slf4j;import org.springframework.stereotype.Controller;

import org.springframework.stereotype.Controller;import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.GetMapping;import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;import org.springframework.web.bind.annotation.RequestParam;



@Controller/**

@Slf4j * Legacy search controller - redirects to unified search

public class SearchController { * Maintained for backward compatibility

 */

    @GetMapping("/yts")@Controller

    public String redirectYts(@RequestParam(value = "term", required = false) String term) {@RequestMapping("/search")

        // Redirect legacy YTS searches to LAT-Team searchpublic class SearchController {

        if (term != null && !term.isEmpty()) {

            return "redirect:/search/latteam?term=" + term;    @GetMapping("/yts")

        }    public String legacySearch(@RequestParam("term") String term) {

        return "redirect:/search/latteam";        // Redirect legacy YTS searches to LAT-Team search

    }        return "redirect:/search/torrents?term=" + term;

    }

    @GetMapping("/yts/movies")}

    public String redirectYtsMovies(@RequestParam(value = "term", required = false) String term) {
        // Redirect legacy YTS movie searches to LAT-Team search
        if (term != null && !term.isEmpty()) {
            return "redirect:/search/latteam?term=" + term;
        }
        return "redirect:/search/latteam";
    }
}