package seedu.address.storage;

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

import seedu.address.model.Library;
import seedu.address.model.Playlist;

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
        String o = currentRelativePath.toAbsolutePath().toString() + "/out/";
        try (Reader reader = new FileReader(s + "library.json")) {
            // Parse JSON to Java
            Library library = gson.fromJson(reader, Library.class);
            LibraryScanner.scan(library);
            List<Playlist> newplaylistlist = new ArrayList<>();
            newplaylistlist.add(new Playlist("asdf"));
            library.setPlaylists(newplaylistlist);
            // Format to JSON
            final String json = gson.toJson(library);
            System.out.println(json);
            try (PrintWriter out = new PrintWriter(s + "library.json")) {
                out.println(json);
            }
        }
    }
}
