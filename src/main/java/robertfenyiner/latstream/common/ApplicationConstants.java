package robertfenyiner.latstream.common;

public final class ApplicationConstants {
    
    // File system constants
    public static final String MUSIC = "Music";
    public static final String VIDEOS = "Videos";
    public static final String DOWNLOADS = "Downloads";
    
    // Torrent constants
    public static final String UDP_TRACKERS = "&tr=udp://tracker.opentrackr.org:1337/announce" +
            "&tr=udp://tracker.openbittorrent.com:80/announce" +
            "&tr=udp://tracker.coppersurfer.tk:6969/announce" +
            "&tr=udp://glotorrents.pw:6969/announce" +
            "&tr=udp://tracker.leechers-paradise.org:6969/announce" +
            "&tr=udp://p4p.arenabg.ch:1337/announce" +
            "&tr=udp://tracker.internetwarriors.net:1337/announce";
    
    // Content types
    public static final String[] VIDEO_EXTENSIONS = {".mp4", ".mkv", ".avi", ".mpeg", ".mov", ".wmv", ".flv"};
    public static final String[] AUDIO_EXTENSIONS = {".mp3", ".flac", ".wav", ".aac", ".ogg", ".m4a"};
    
    // Application settings
    public static final String APP_NAME = "LatStream";
    public static final String USER_AGENT = "LatStream/1.0";
    
    private ApplicationConstants() {
        // Private constructor to prevent instantiation
    }
}