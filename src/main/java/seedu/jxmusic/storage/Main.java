package seedu.jxmusic.storage;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import seedu.jxmusic.model.Library;
import seedu.jxmusic.model.Name;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.storage.jsonserdes.LibraryDeserializer;
import seedu.jxmusic.storage.jsonserdes.LibrarySerializer;
import seedu.jxmusic.storage.jsonserdes.PlaylistDeserializer;
import seedu.jxmusic.storage.jsonserdes.PlaylistSerializer;

/**
 * tries out the serializers
 */
public class Main {
    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(final String[] args) throws IOException {
        // Configure GSON
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Library.class, new LibraryDeserializer());
        gsonBuilder.registerTypeAdapter(Playlist.class, new PlaylistDeserializer());
        gsonBuilder.registerTypeAdapter(Library.class, new LibrarySerializer());
        gsonBuilder.registerTypeAdapter(Playlist.class, new PlaylistSerializer());
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        // The JSON data
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString() + "/library/";
        try (Reader reader = new FileReader(s + "library.json")) {
            // Parse JSON to Java
            Library library = gson.fromJson(reader, Library.class);
            library.setTracks(TracksScanner.scan(Paths.get(Library.LIBRARYDIR)));

            List<Playlist> newplaylistlist = new ArrayList<>();
            Name nameNewplaylist = new Name("asdf");
            newplaylistlist.add(new Playlist(nameNewplaylist));
            library.setPlaylists(newplaylistlist);

            // Format to JSON
            final String json = gson.toJson(library);
            try (PrintWriter out = new PrintWriter(s + "library.json")) {
                out.println(json);
            }
        }
    }
}
