package robertfenyiner.latstream.content;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
@RequestMapping("/stream")
@RequiredArgsConstructor
public class StreamController {

    private final VideoRepository videoRepository;

    @GetMapping("/video/{movieCode}")
    public String getVideoPlayer(@PathVariable("movieCode") String movieCode, Model model) {
        log.info("Streaming video with movieCode: {}", movieCode);
        
        model.addAttribute("movieCode", movieCode);
        var video = videoRepository.findById(URLDecoder.decode(movieCode, StandardCharsets.UTF_8));
        
        if (video.isPresent()) {
            String contentMimeType = video.get().getContentMimeType();

            // Workaround to get MKV Videos playing on Chromium browsers
            if (contentMimeType.equals("video/x-matroska")) {
                contentMimeType = "video/webm";
            }

            model.addAttribute("contentMimeType", contentMimeType);
            model.addAttribute("video", video.get());
            return "player :: videoPlayer";
        } else {
            log.warn("Video not found for movieCode: {}", movieCode);
            model.addAttribute("error", "Video no encontrado");
            return "error";
        }
    }

}