package seedu.jxmusic.storage;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.jxmusic.model.Library;
import seedu.jxmusic.model.Track;

/**
 * Scan through the library directory to get all the MP3 files.
 */
public class TracksScanner {
    /**
     * Scans the library directory to get a set of tracks
     * @return set of tracks in the library directory
     */
    public static Set<Track> scan(Path libraryDir) {
        File folder = libraryDir.toFile();
        File[] trackFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(Track.MP3_EXTENSION));
        Set<Track> trackSet = new HashSet<>();
        for (File trackFile : trackFiles) {
            trackSet.add(new Track(trackFile));
        }
        return trackSet;
    }
}
