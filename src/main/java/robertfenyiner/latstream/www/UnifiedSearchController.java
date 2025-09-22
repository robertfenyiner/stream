package com.robertfenyiner.latstream.www;package com.robertfenyiner.latstream.www;package com.robertfenyiner.latstream.www;package com.robertfenyiner.latstream.www;



import com.robertfenyiner.latstream.preferences.Preference;

import com.robertfenyiner.latstream.preferences.UserPreferences;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;import com.robertfenyiner.latstream.preferences.Preference;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;import com.robertfenyiner.latstream.preferences.UserPreferences;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Controller;import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;import com.robertfenyiner.latstream.preferences.Preference;import com.robertfenyiner.latstream.preferences.Preference;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;import lombok.extern.slf4j.Slf4j;import com.robertfenyiner.latstream.preferences.UserPreferences;import com.robertfenyiner.latstream.preferences.UserPreferences;



import java.util.Optional;import org.springframework.beans.factory.annotation.Value;



/**import org.springframework.stereotype.Controller;import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;

 * Search Controller for LAT-Team private tracker using UNIT3D API

 */import org.springframework.ui.Model;

@Slf4j

@Controllerimport org.springframework.web.bind.annotation.GetMapping;import lombok.RequiredArgsConstructor;import lombok.RequiredArgsConstructor;

@RequestMapping("/search")

@RequiredArgsConstructorimport org.springframework.web.bind.annotation.RequestMapping;

public class UnifiedSearchController {

import org.springframework.web.bind.annotation.RequestParam;import lombok.extern.slf4j.Slf4j;import lombok.extern.slf4j.Slf4j;

    private final Optional<UNIT3DAPIClient> unit3dAPIClient;

    private final UserPreferences userPreferences;



    @Value("${unit3d.api.enabled:false}")import java.util.Optional;import org.springframework.beans.factory.annotation.Value;import org.springframework.beans.factory.annotation.Value;

    private boolean unit3dEnabled;



    @GetMapping("/torrents")

    public String searchTorrents(@RequestParam("term") String term, Model model) {/**import org.springframework.stereotype.Controller;import org.springframework.stereotype.Controller;

        Optional<Preference> darkModePreference = userPreferences.findById(1);

        boolean darkModeEnabled = darkModePreference.map(Preference::isEnabled).orElse(false); * Search Controller for LAT-Team private tracker using UNIT3D API

        model.addAttribute("darkmodeenabled", darkModeEnabled);

         */import org.springframework.ui.Model;import org.springframework.ui.Model;

        if (term == null || term.trim().isEmpty()) {

            return "index";@Slf4j

        }

@Controllerimport org.springframework.web.bind.annotation.GetMapping;import org.springframework.web.bind.annotation.GetMapping;

        try {

            if (unit3dEnabled && unit3dAPIClient.isPresent()) {@RequestMapping("/search")

                log.info("Searching LAT-Team private tracker for: {}", term);

                var results = unit3dAPIClient.get().searchTorrents(term, 1, 50);@RequiredArgsConstructorimport org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RequestMapping;

                model.addAttribute("unit3dResults", results.data());

                model.addAttribute("searchProvider", "LAT-Team");public class UnifiedSearchController {

                return "index :: unit3d-search-results";

            } else {import org.springframework.web.bind.annotation.RequestParam;import org.springframework.web.bind.annotation.RequestParam;

                model.addAttribute("searchError", "LAT-Team API is not enabled");

                return "index :: search-error";    private final Optional<UNIT3DAPIClient> unit3dAPIClient;

            }

        } catch (Exception e) {    private final UserPreferences userPreferences;

            log.error("Error searching torrents: {}", e.getMessage());

            model.addAttribute("searchError", "Error searching torrents");

            return "index :: search-error";

        }    @Value("${unit3d.api.enabled:false}")import java.util.Optional;import java.util.Optional;

    }

    private boolean unit3dEnabled;

    @HxRequest

    @GetMapping("/torrents")

    public String searchTorrentsAsync(@RequestParam("term") String term, Model model) {

        Optional<Preference> darkModePreference = userPreferences.findById(1);    @GetMapping("/torrents")

        boolean darkModeEnabled = darkModePreference.map(Preference::isEnabled).orElse(false);

        model.addAttribute("darkmodeenabled", darkModeEnabled);    public String searchTorrents(@RequestParam("term") String term, Model model) {/**/**

        

        if (term == null || term.trim().isEmpty()) {        Optional<Preference> darkModePreference = userPreferences.findById(1);

            return "";

        }        boolean darkModeEnabled = darkModePreference.map(Preference::isEnabled).orElse(false); * Search Controller for LAT-Team private tracker using UNIT3D API * Search Controller for LAT-Team private tracker using UNIT3D API



        try {        model.addAttribute("darkmodeenabled", darkModeEnabled);

            if (unit3dEnabled && unit3dAPIClient.isPresent()) {

                log.info("Async searching LAT-Team private tracker for: {}", term);         */ */

                var results = unit3dAPIClient.get().searchTorrents(term, 1, 50);

                model.addAttribute("unit3dResults", results.data());        if (term == null || term.trim().isEmpty()) {

                model.addAttribute("searchProvider", "LAT-Team");

                return "index :: unit3d-search-results";            return "index";@Slf4j@Slf4j

            } else {

                model.addAttribute("searchError", "LAT-Team API is not enabled");        }

                return "index :: search-error";

            }@Controller@Controller

        } catch (Exception e) {

            log.error("Error async searching torrents: {}", e.getMessage());        try {

            model.addAttribute("searchError", "Error searching torrents");

            return "index :: search-error";            if (unit3dEnabled && unit3dAPIClient.isPresent()) {@RequestMapping("/search")@RequestMapping("/search")

        }

    }                log.info("Searching LAT-Team private tracker for: {}", term);



    @GetMapping("/unit3d/latest")                var results = unit3dAPIClient.get().searchTorrents(term, 1, 50);@RequiredArgsConstructor@RequiredArgsConstructor

    public String getLatestTorrents(Model model) {

        if (!unit3dEnabled || unit3dAPIClient.isEmpty()) {                model.addAttribute("unit3dResults", results.data());

            model.addAttribute("error", "LAT-Team API is not enabled");

            return "error";                model.addAttribute("searchProvider", "LAT-Team");public class UnifiedSearchController {public class UnifiedSearchController {

        }

                return "index :: unit3d-search-results";

        try {

            var results = unit3dAPIClient.get().getLatestTorrents(1, 20);            } else {

            model.addAttribute("unit3dResults", results.data());

            model.addAttribute("searchProvider", "LAT-Team");                model.addAttribute("searchError", "LAT-Team API is not enabled");

            return "index :: unit3d-search-results";

        } catch (Exception e) {                return "index :: search-error";    private final Optional<UNIT3DAPIClient> unit3dAPIClient;    private final Optional<UNIT3DAPIClient> unit3dAPIClient;

            log.error("Error getting latest torrents: {}", e.getMessage());

            model.addAttribute("searchError", "Error getting latest torrents");            }

            return "index :: search-error";

        }        } catch (Exception e) {    private final UserPreferences userPreferences;    private final UserPreferences userPreferences;

    }

            log.error("Error searching torrents: {}", e.getMessage());

    @GetMapping("/unit3d/freeleech")

    public String getFreeleechTorrents(Model model) {            model.addAttribute("searchError", "Error searching torrents");

        if (!unit3dEnabled || unit3dAPIClient.isEmpty()) {

            model.addAttribute("error", "LAT-Team API is not enabled");            return "index :: search-error";

            return "error";

        }        }    @Value("${unit3d.api.enabled:false}")    @Value("${unit3d.api.enabled:false}")



        try {    }

            var results = unit3dAPIClient.get().getFreeleechTorrents(1, 20);

            model.addAttribute("unit3dResults", results.data());    private boolean unit3dEnabled;    private boolean unit3dEnabled;

            model.addAttribute("searchProvider", "LAT-Team");

            return "index :: unit3d-search-results";    @HxRequest

        } catch (Exception e) {

            log.error("Error getting freeleech torrents: {}", e.getMessage());    @GetMapping("/torrents")

            model.addAttribute("searchError", "Error getting freeleech torrents");

            return "index :: search-error";    public String searchTorrentsAsync(@RequestParam("term") String term, Model model) {

        }

    }        Optional<Preference> darkModePreference = userPreferences.findById(1);    @GetMapping("/torrents")    @GetMapping("/torrents")

}
        boolean darkModeEnabled = darkModePreference.map(Preference::isEnabled).orElse(false);

        model.addAttribute("darkmodeenabled", darkModeEnabled);    public String searchTorrents(@RequestParam("term") String term, Model model) {    public String searchTorrents(@RequestParam("term") String term, Model model) {

        

        if (term == null || term.trim().isEmpty()) {        Optional<Preference> darkModePreference = userPreferences.findById(1);        Optional<Preference> darkModePreference = userPreferences.findById(1);

            return "";

        }        boolean darkModeEnabled = darkModePreference.map(Preference::isEnabled).orElse(false);        boolean darkModeEnabled = darkModePreference.map(Preference::isEnabled).orElse(false);



        try {        model.addAttribute("darkmodeenabled", darkModeEnabled);        model.addAttribute("darkmodeenabled", darkModeEnabled);

            if (unit3dEnabled && unit3dAPIClient.isPresent()) {

                log.info("Async searching LAT-Team private tracker for: {}", term);                

                var results = unit3dAPIClient.get().searchTorrents(term, 1, 50);

                model.addAttribute("unit3dResults", results.data());        if (term == null || term.trim().isEmpty()) {        if (term == null || term.trim().isEmpty()) {

                model.addAttribute("searchProvider", "LAT-Team");

                return "index :: unit3d-search-results";            return "index";            return "index";

            } else {

                model.addAttribute("searchError", "LAT-Team API is not enabled");        }        }

                return "index :: search-error";

            }

        } catch (Exception e) {

            log.error("Error async searching torrents: {}", e.getMessage());        try {        try {

            model.addAttribute("searchError", "Error searching torrents");

            return "index :: search-error";            if (unit3dEnabled && unit3dAPIClient.isPresent()) {            if (unit3dEnabled && unit3dAPIClient.isPresent()) {

        }

    }                // Use UNIT3D private tracker (LAT-Team)                // Use UNIT3D private tracker (LAT-Team)



    @GetMapping("/unit3d/latest")                log.info("Searching LAT-Team private tracker for: {}", term);                log.info("Searching LAT-Team private tracker for: {}", term);

    public String getLatestTorrents(Model model) {

        if (!unit3dEnabled || unit3dAPIClient.isEmpty()) {                var results = unit3dAPIClient.get().searchTorrents(term, 1, 50);                var results = unit3dAPIClient.get().searchTorrents(term, 1, 50);

            model.addAttribute("error", "LAT-Team API is not enabled");

            return "error";                model.addAttribute("unit3dResults", results.data());                model.addAttribute("unit3dResults", results.data());

        }

                model.addAttribute("searchProvider", "LAT-Team");                model.addAttribute("searchProvider", "LAT-Team");

        try {

            var results = unit3dAPIClient.get().getLatestTorrents(1, 20);                return "index :: unit3d-search-results";                return "index :: unit3d-search-results";

            model.addAttribute("unit3dResults", results.data());

            model.addAttribute("searchProvider", "LAT-Team");            } else {            } else {

            return "index :: unit3d-search-results";

        } catch (Exception e) {                model.addAttribute("searchError", "LAT-Team API is not enabled");                model.addAttribute("searchError", "LAT-Team API is not enabled");

            log.error("Error getting latest torrents: {}", e.getMessage());

            model.addAttribute("searchError", "Error getting latest torrents");                return "index :: search-error";                return "index :: search-error";

            return "index :: search-error";

        }            }            }

    }

        } catch (Exception e) {        } catch (Exception e) {

    @GetMapping("/unit3d/freeleech")

    public String getFreeleechTorrents(Model model) {            log.error("Error searching torrents: {}", e.getMessage());            log.error("Error searching torrents: {}", e.getMessage());

        if (!unit3dEnabled || unit3dAPIClient.isEmpty()) {

            model.addAttribute("error", "LAT-Team API is not enabled");            model.addAttribute("searchError", "Error searching torrents");            model.addAttribute("searchError", "Error searching torrents");

            return "error";

        }            return "index :: search-error";            return "index :: search-error";



        try {        }        }

            var results = unit3dAPIClient.get().getFreeleechTorrents(1, 20);

            model.addAttribute("unit3dResults", results.data());    }    }

            model.addAttribute("searchProvider", "LAT-Team");

            return "index :: unit3d-search-results";

        } catch (Exception e) {

            log.error("Error getting freeleech torrents: {}", e.getMessage());    @HxRequest    @HxRequest

            model.addAttribute("searchError", "Error getting freeleech torrents");

            return "index :: search-error";    @GetMapping("/torrents")    @GetMapping("/torrents")

        }

    }    public String searchTorrentsAsync(@RequestParam("term") String term, Model model) {    public String searchTorrentsAsync(@RequestParam("term") String term, Model model) {

}
        Optional<Preference> darkModePreference = userPreferences.findById(1);        Optional<Preference> darkModePreference = userPreferences.findById(1);

        boolean darkModeEnabled = darkModePreference.map(Preference::isEnabled).orElse(false);        boolean darkModeEnabled = darkModePreference.map(Preference::isEnabled).orElse(false);

        model.addAttribute("darkmodeenabled", darkModeEnabled);        model.addAttribute("darkmodeenabled", darkModeEnabled);

                

        if (term == null || term.trim().isEmpty()) {        if (term == null || term.trim().isEmpty()) {

            return "";            return "";

        }        }



        try {        try {

            if (unit3dEnabled && unit3dAPIClient.isPresent()) {            if (unit3dEnabled && unit3dAPIClient.isPresent()) {

                // Use UNIT3D private tracker (LAT-Team)                // Use UNIT3D private tracker (LAT-Team)

                log.info("Async searching LAT-Team private tracker for: {}", term);                log.info("Async searching LAT-Team private tracker for: {}", term);

                var results = unit3dAPIClient.get().searchTorrents(term, 1, 50);                var results = unit3dAPIClient.get().searchTorrents(term, 1, 50);

                model.addAttribute("unit3dResults", results.data());                model.addAttribute("unit3dResults", results.data());

                model.addAttribute("searchProvider", "LAT-Team");                model.addAttribute("searchProvider", "LAT-Team");

                return "index :: unit3d-search-results";                return "index :: unit3d-search-results";

            } else {            } else {

                model.addAttribute("searchError", "LAT-Team API is not enabled");                model.addAttribute("searchError", "LAT-Team API is not enabled");

                return "index :: search-error";                return "index :: search-error";

            }            }

        } catch (Exception e) {        } catch (Exception e) {

            log.error("Error async searching torrents: {}", e.getMessage());            log.error("Error async searching torrents: {}", e.getMessage());

            model.addAttribute("searchError", "Error searching torrents");            model.addAttribute("searchError", "Error searching torrents");

            return "index :: search-error";            return "index :: search-error";

        }        }

    }    }



    // LAT-Team specific endpoints    // LAT-Team specific endpoints

    @GetMapping("/unit3d/latest")    @GetMapping("/unit3d/latest")

    public String getLatestTorrents(Model model) {    public String getLatestTorrents(Model model) {

        if (!unit3dEnabled || unit3dAPIClient.isEmpty()) {        if (!unit3dEnabled || unit3dAPIClient.isEmpty()) {

            model.addAttribute("error", "LAT-Team API is not enabled");            model.addAttribute("error", "LAT-Team API is not enabled");

            return "error";            return "error";

        }        }



        try {        try {

            var results = unit3dAPIClient.get().getLatestTorrents(1, 20);            var results = unit3dAPIClient.get().getLatestTorrents(1, 20);

            model.addAttribute("unit3dResults", results.data());            model.addAttribute("unit3dResults", results.data());

            model.addAttribute("searchProvider", "LAT-Team");            model.addAttribute("searchProvider", "LAT-Team");

            return "index :: unit3d-search-results";            return "index :: unit3d-search-results";

        } catch (Exception e) {        } catch (Exception e) {

            log.error("Error getting latest torrents: {}", e.getMessage());            log.error("Error getting latest torrents: {}", e.getMessage());

            model.addAttribute("searchError", "Error getting latest torrents");            model.addAttribute("searchError", "Error getting latest torrents");

            return "index :: search-error";            return "index :: search-error";

        }        }

    }    }



    @GetMapping("/unit3d/freeleech")    @GetMapping("/unit3d/freeleech")

    public String getFreeleechTorrents(Model model) {    public String getFreeleechTorrents(Model model) {

        if (!unit3dEnabled || unit3dAPIClient.isEmpty()) {        if (!unit3dEnabled || unit3dAPIClient.isEmpty()) {

            model.addAttribute("error", "LAT-Team API is not enabled");            model.addAttribute("error", "LAT-Team API is not enabled");

            return "error";            return "error";

        }        }



        try {        try {

            var results = unit3dAPIClient.get().getFreeleechTorrents(1, 20);            var results = unit3dAPIClient.get().getFreeleechTorrents(1, 20);

            model.addAttribute("unit3dResults", results.data());            model.addAttribute("unit3dResults", results.data());

            model.addAttribute("searchProvider", "LAT-Team");            model.addAttribute("searchProvider", "LAT-Team");

            return "index :: unit3d-search-results";            return "index :: unit3d-search-results";

        } catch (Exception e) {        } catch (Exception e) {

            log.error("Error getting freeleech torrents: {}", e.getMessage());            log.error("Error getting freeleech torrents: {}", e.getMessage());

            model.addAttribute("searchError", "Error getting freeleech torrents");            model.addAttribute("searchError", "Error getting freeleech torrents");

            return "index :: search-error";            return "index :: search-error";

        }        }

    }    }

}}
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
