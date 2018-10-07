package seedu.address.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import seedu.address.model.Library;
import seedu.address.model.Track;

/**
 * Scan through the library directory to get all the MP3 files.
 */
public class LibraryScanner {
    /**
     * scanner method
     *
     * @return
     */
    public static void scan(Library library) {
        File folder = new File(Library.LIBRARYDIR);
        File[] listOfFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));
        List<Track> trackList = new ArrayList<>();
        for (int i = 0; i < listOfFiles.length; i++) {
            try {
                trackList.add(new Track(listOfFiles[i]));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        library.setTracks(trackList);
    }
}
