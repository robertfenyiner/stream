package robertfenyiner.latstream.common;

public enum DOWNLOADTYPE {
    SEQUENTIAL("sequential"),
    RANDOMIZED("randomized");
    
    private final String value;
    
    DOWNLOADTYPE(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}