package robertfenyiner.latstream.torrent;

import lombok.Data;

import java.io.File;

@Data
public class Options {

    private File metainfoFile;
    private String magnetUri;
    private String torrentHash;
    private File targetDirectory;
    private boolean seedAfterDownloaded;
    private boolean sequential;
    private boolean enforceEncryption;
    private boolean disableUi;
    private boolean disableTorrentStateLogs;
    private boolean verboseLogging;
    private boolean traceLogging;
    private String iface;
    private Integer port;
    private Integer dhtPort;
    private boolean downloadAllFiles;

}