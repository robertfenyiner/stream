package robertfenyiner.latstream.common;

public enum CONTENTTYPE {
    VIDEO("video"),
    AUDIO("audio"), 
    OTHER("other");
    
    private final String value;
    
    CONTENTTYPE(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}