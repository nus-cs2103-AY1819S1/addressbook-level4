package seedu.jxmusic.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import seedu.jxmusic.model.Track;

/**
 * Scan through the library directory to get all the MP3 files.
 */
public class TracksScanner {
    /**
     * Scans the library directory to get a set of tracks
     * @return set of tracks in the library directory
     */
    public static ObservableSet<Track> scan(Path libraryDir) {
        File folder = libraryDir.toFile();
        if (!folder.exists()) {
            try {
                libraryDir.toFile().mkdirs();
                String src = Paths.get("").toAbsolutePath().toString() + "/src/main/resources/library/";
                Files.walk(Paths.get(src)).filter(p -> p.toString().endsWith(".mp3")).forEach(a -> {
                    System.out.println(a);
                    Path b = Paths.get(libraryDir.toString(), a.toString().substring(src.length()));
                    try {
                        if (!a.toString().equals(src)) {
                            Files.copy(a, b, new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
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
