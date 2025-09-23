package robertfenyiner.latstream.content;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/personal")
@RequiredArgsConstructor
public class PersonalContentController {

    private final VideoRepository videoRepository;
    private final MusicRepository musicRepository;

    @GetMapping("/media")
    String getPersonalMedia(Model model) {
        model.addAttribute("videos", videoRepository.findAll());
        model.addAttribute("music", musicRepository.findAll());
        return "personalmedia :: personalMediaPlayer";
    }

    @GetMapping("")
    String getPersonalMediaPage(Model model) {
        model.addAttribute("videos", videoRepository.findAll());
        model.addAttribute("music", musicRepository.findAll());
        return "personalmedia";
    }
}