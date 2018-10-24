package seedu.jxmusic.storage;

import java.io.File;
import java.nio.file.Path;
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
