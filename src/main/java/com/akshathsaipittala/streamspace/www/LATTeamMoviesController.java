package com.akshathsaipittala.streamspace.www;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/latteam")
@RequiredArgsConstructor
public class LATTeamMoviesController {
    final LATTeamAPIClient latteamApiClient;

    @GetMapping("/movies/{id}")
    String getLatteamMovieOverview(@PathVariable("id") int id, Model model) {
        model.addAttribute("latteamMovieRecord", latteamApiClient.getMovieDetails(id));
        // Si hay sugerencias, adaptarlas aquí
        // model.addAttribute("latteamSuggestedRecord", ...);
        return "latteamMovie :: latteamMovieOverview";
    }

    @GetMapping("/movies/cat/{category}")
    String viewAllPage(Model model,
                       @RequestParam(defaultValue = "1") int page,
                       @PathVariable("category") String category) {
        model.addAttribute("category", category);
        // Adaptar lógica de categorías a la API de LAT-Team
        // model.addAttribute("latteamMoviesRecord", ...);
        model.addAttribute("currentPage", page);
        return "viewAll :: gallery";
    }

    // Métodos para latest, most seeded, etc. según API LAT-Team
    @GetMapping("/latest")
    String latest(Model model) {
        model.addAttribute("latteamLatestRecord", latteamApiClient.getLatestMovies(1, 20));
        return "movies :: latteamLatest";
    }

    @GetMapping("/mostseeded")
    String mostSeeded(Model model) {
        model.addAttribute("latteamMostSeededRecord", latteamApiClient.getMostSeededMovies(1, 20));
        return "movies :: latteamMostSeeded";
    }

    // Agregar más métodos según necesidades de navegación
}
