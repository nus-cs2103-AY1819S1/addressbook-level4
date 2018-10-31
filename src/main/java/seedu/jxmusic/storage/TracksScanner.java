package seedu.jxmusic.storage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import seedu.jxmusic.MainApp;
import seedu.jxmusic.model.Track;

/**
 * Scan through the library directory to get all the MP3 files.
 * If library directory doesn't exist, copy from resources/library/* to library directory
 */
public class TracksScanner {
    /**
     * Scans the library directory to get a set of tracks
     * @return set of tracks in the library directory
     */
    public static ObservableSet<Track> scan(Path libraryDir) {
        File folder = libraryDir.toFile();
        if (!folder.exists() || folder.listFiles().length == 0) {
            try {
                final File jarFile = new File(MainApp.class
                        .getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .getPath());
                if (jarFile.isFile()) {
                    Runtime rt = Runtime.getRuntime();
                    // runs command line: `unzip jxmusic.jar "library/*"`
                    Process pr = rt.exec("unzip " + jarFile.getName() + " \"library/*\"");
                    pr.waitFor();
                } else {
                    URL src = MainApp.class.getResource("/library/");
                    libraryDir.toFile().mkdirs();
                    File f = Paths.get(src.toURI()).toFile();
                    Files.walk(Paths.get(f.getAbsolutePath())).filter(p -> p.toString().endsWith(".mp3")).forEach(a -> {
                        Path b = Paths.get(libraryDir.toString(), a.toString().substring(src.toString().length() - 6));
                        try {
                            if (!a.toString().equals(src)) {
                                Files.copy(a, b, new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            } catch (IOException | URISyntaxException | InterruptedException e) {
                //permission issue
                e.printStackTrace();
            }
        }
        File[] trackFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(Track.MP3_EXTENSION));
        ObservableSet<Track> trackSet = FXCollections.observableSet(new HashSet<>());
        for (File trackFile : trackFiles) {
            try {
                trackSet.add(new Track(trackFile));
            } catch (IllegalArgumentException e) {
                // do nothing, skip invalid track
            }
        }
        return trackSet;
    }
}
