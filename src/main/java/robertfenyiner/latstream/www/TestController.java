package robertfenyiner.latstream.www;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import robertfenyiner.latstream.config.UNIT3DConfig;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private UNIT3DConfig unit3dConfig;

    @GetMapping("/config")
    public ResponseEntity<?> testConfig() {
        return ResponseEntity.ok()
            .body("Base URL: " + unit3dConfig.getBaseUrl() + 
                  ", API Key length: " + (unit3dConfig.getApiKey() != null ? unit3dConfig.getApiKey().length() : "null"));
    }
}
