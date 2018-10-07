package seedu.jxmusic.storage;

import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import seedu.jxmusic.model.Library;
import seedu.jxmusic.model.Playlist1;

/**
 * Read the library.json file and scan all the mp3 files from the library directory, a combination of
 * LibraryDeserializer and LibraryScanner
 */
public class LibraryRead {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // Configure Gson
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Library.class, new LibraryDeserializer());
        gsonBuilder.registerTypeAdapter(Playlist1.class, new PlaylistDeserializer());
        Gson gson = gsonBuilder.create();

        // The JSON data
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString() + "/library/";
        try (Reader reader = new FileReader(s + "library.json")) {
            // Parse JSON to Java
            Library library = gson.fromJson(reader, Library.class);
            LibraryScanner.scan(library);
        }
    }
}
