package seedu.jxmusic.storage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import seedu.jxmusic.commons.exceptions.DataConversionException;
import seedu.jxmusic.model.Library;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.storage.jsonserdes.LibraryDeserializer;
import seedu.jxmusic.storage.jsonserdes.LibrarySerializer;
import seedu.jxmusic.storage.jsonserdes.PlaylistDeserializer;
import seedu.jxmusic.storage.jsonserdes.PlaylistSerializer;

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
     * Returns library in the file or an empty library
     */
    public static Library loadDataFromFile(Path file) throws DataConversionException, FileNotFoundException {
        // Configure Gson
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Library.class, new LibraryDeserializer());
        gsonBuilder.registerTypeAdapter(Playlist.class, new PlaylistDeserializer());
        Gson gson = gsonBuilder.create();
        // The JSON data
        Reader reader = new FileReader(file.toString());
        Library library;
        try {
            // Parse JSON to Java
            library = gson.fromJson(reader, Library.class);
        } catch (JsonIOException | JsonSyntaxException | InvalidPathException e) {
            throw new DataConversionException(e);
        }

        return library;
    }
}
