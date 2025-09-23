package robertfenyiner.latstream.common;

public enum SOURCE {
    LOCAL("local"),
    TORRENT("torrent"),
    LATTEAM("latteam");
    
    private final String value;
    
    SOURCE(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}