package com.akshathsaipittala.streamspace.downloads;

import com.akshathsaipittala.streamspace.helpers.DownloadTask;
import com.akshathsaipittala.streamspace.helpers.CONTENTTYPE;
import com.akshathsaipittala.streamspace.helpers.DOWNLOADTYPE;
import com.akshathsaipittala.streamspace.torrentengine.TorrentDownloadManager;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Repository
public interface Downloads extends ListCrudRepository<DownloadTask, String> {
}

@Slf4j
@Controller
@RequestMapping("/download")
@RequiredArgsConstructor
class DownloadsController {

    final Downloads downloads;
    final TorrentDownloadManager torrentDownloadManager;

    @GetMapping("")
    String getAllDownloads(Model model) {
        List<DownloadTask> listOfDownloads = downloads.findAll();

        if (listOfDownloads.isEmpty()) {
            return "downloads :: showNoDownloads";
        } else {
            model.addAttribute("tasks", listOfDownloads);
            return "downloads :: showAllDownloads";

        }
    }

    @ResponseBody
    @GetMapping(value = "/count", produces= MediaType.TEXT_HTML_VALUE)
    String count() {
        return String.valueOf(downloads.count());
        //return "<span class=\"badge text-bg-secondary\">"+ downloadTaskRepository.count() +"</span>";
    }

    @GetMapping("/form")
    String downloadForm() {
        return "downloads :: downloadTorrent";
    }

    /*@PostMapping("/togglePause")
    public ResponseEntity<String> togglePause() {
        TorrentClient.toggleStartStop();
        return ResponseEntity.ok("Toggled Pause! Click to Resume");
    }*/

    @HxRequest
    @PostMapping("/torrent")
    String downloadTorrent(
            @RequestParam("selectedOption") String torrentHash,
            @RequestParam(value = "sequentialCheck", required = false) String sequentialCheck,
            @RequestParam(value = "torrentName", required = false) String torrentName,
            Model model) {
        log.info("Selected Option: {}", torrentHash);
        log.info("Strategy: {}", sequentialCheck);
        model.addAttribute("torrentHash", torrentHash);

        DownloadTask task;
        if (sequentialCheck != null && sequentialCheck.equals("on")) {
            task = new DownloadTask(torrentHash, torrentName !=null ? torrentName:torrentHash, torrentHash, CONTENTTYPE.VIDEO, DOWNLOADTYPE.SEQUENTIAL);
        } else {
            task = new DownloadTask(torrentHash, torrentName !=null ? torrentName:torrentHash, torrentHash, CONTENTTYPE.VIDEO, DOWNLOADTYPE.RANDOMIZED);
        }
        torrentDownloadManager.startDownload(task);
        return "downloads :: downloadProgress";
    }

    @HxRequest
    @PostMapping("/torrent/{torrentHash}")
    String downloadTorrent(Model model,@PathVariable String torrentHash) {
        log.info("Selected Option: {}", torrentHash);

        DownloadTask task = new DownloadTask(torrentHash, torrentHash, torrentHash, CONTENTTYPE.AUDIO, DOWNLOADTYPE.SEQUENTIAL);
        torrentDownloadManager.startDownload(task);
        model.addAttribute("torrentHash", torrentHash);
        return "downloads :: downloadProgress";
    }

    @PostMapping("/pause/{hashString}")
    ResponseEntity<String> pauseDownload(@PathVariable("hashString") String pauseHash) {
        torrentDownloadManager.pauseDownload(pauseHash);
        return ResponseEntity.ok("<i hx-post=/download/torrent/" + pauseHash+ " class=\"bi bi-arrow-clockwise\" hx-target=\"#download-container\" hx-swap=\"outerHTML\"></i>");
    }

    @PostMapping("/cancel/{hashString}")
    ResponseEntity<String> cancelDownload(@PathVariable("hashString") String cancelHash) {
        torrentDownloadManager.cancelDownload(cancelHash);
        return ResponseEntity.ok("");
    }
}