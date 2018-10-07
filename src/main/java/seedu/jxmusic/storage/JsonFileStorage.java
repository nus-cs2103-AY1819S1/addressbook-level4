package seedu.jxmusic.storage;

import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import seedu.jxmusic.model.Library;
import seedu.jxmusic.model.Playlist;

/**
 * Stores library data in an XML file
 */
public class JsonFileStorage {
    /**
     * Saves the given library data to the specified file.
     */
    public static void saveDataToFile(Path file, Library library)
            throws Exception {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Library.class, new LibrarySerializer());
        gsonBuilder.registerTypeAdapter(Playlist.class, new PlaylistSerializer());
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        final String json = gson.toJson(library);
        try (PrintWriter out = new PrintWriter(file.toString())) {
            out.println(json);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns jxmusic in the file or an empty jxmusic
     */
    public static Library loadDataFromSaveFile(Path file) throws Exception {
        // Configure Gson
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Library.class, new LibraryDeserializer());
        gsonBuilder.registerTypeAdapter(Playlist.class, new PlaylistDeserializer());
        Gson gson = gsonBuilder.create();
        Library library = new Library();
        // The JSON data
        Reader reader = new FileReader(file.toString());
        // Parse JSON to Java
        library = gson.fromJson(reader, Library.class);
        LibraryScanner.scan(library);

        return library;
    }
}
