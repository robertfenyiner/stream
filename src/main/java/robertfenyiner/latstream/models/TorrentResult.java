package robertfenyiner.latstream.models;

public class TorrentResult {
    private String id;
    private String name;
    private String category;
    private String size;
    private String downloadUrl;

    public TorrentResult() {}

    public TorrentResult(String id, String name, String category, String size) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.size = size;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}