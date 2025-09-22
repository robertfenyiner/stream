package robertfenyiner.latstream.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import robertfenyiner.latstream.config.UNIT3DConfig;
import robertfenyiner.latstream.models.TorrentResult;
import java.util.ArrayList;
import java.util.List;

@Service
public class LatTeamSearchService {

    @Autowired
    private UNIT3DConfig unit3dConfig;

    @Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<TorrentResult> searchTorrents(String query) {
        System.out.println("=== BÚSQUEDA LAT-TEAM ===");
        System.out.println("Query: " + query);
        
        List<TorrentResult> results = new ArrayList<>();
        
        try {
            // Configurar headers con autenticación
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + unit3dConfig.getApiKey());
            headers.set("Accept", "application/json");
            headers.set("Content-Type", "application/json");
            headers.set("User-Agent", "LatStream/1.0");
            
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            String url = unit3dConfig.getBaseUrl() + "/api/torrents/filter?name=" + query;
            System.out.println("URL: " + url);
            System.out.println("API Key: " + unit3dConfig.getApiKey().substring(0, 10) + "...");
            
            ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, String.class);
            
            System.out.println("Status: " + response.getStatusCode());
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                System.out.println("Response preview: " + response.getBody().substring(0, Math.min(200, response.getBody().length())));
                
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode dataArray = root.get("data");
                
                if (dataArray != null && dataArray.isArray()) {
                    System.out.println("Procesando " + dataArray.size() + " torrents...");
                    
                    for (JsonNode torrentNode : dataArray) {
                        JsonNode attributes = torrentNode.get("attributes");
                        if (attributes != null) {
                            String id = torrentNode.get("id").asText();
                            String name = attributes.get("name").asText();
                            String category = attributes.get("category").asText();
                            long sizeBytes = attributes.get("size").asLong();
                            String size = formatSize(sizeBytes);
                            
                            results.add(new TorrentResult(id, name, category, size));
                            System.out.println("✓ " + name + " (" + size + ")");
                        }
                    }
                } else {
                    System.out.println("No se encontró array 'data' en la respuesta");
                }
            } else {
                System.out.println("Error HTTP: " + response.getStatusCode());
                System.out.println("Response body: " + response.getBody());
            }
            
        } catch (Exception e) {
            System.err.println("Error en búsqueda LAT-Team: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("=== RESULTADO: " + results.size() + " torrents ===");
        return results;
    }

    private String formatSize(long bytes) {
        if (bytes == 0) return "0 B";
        int k = 1024;
        String[] sizes = {"B", "KB", "MB", "GB", "TB"};
        int i = (int) Math.floor(Math.log(bytes) / Math.log(k));
        return String.format("%.1f %s", bytes / Math.pow(k, i), sizes[i]);
    }
}