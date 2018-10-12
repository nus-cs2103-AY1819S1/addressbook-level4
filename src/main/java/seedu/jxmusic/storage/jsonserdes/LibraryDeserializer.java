package seedu.jxmusic.storage.jsonserdes;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import seedu.jxmusic.model.Library;
import seedu.jxmusic.model.Playlist;

/**
 * This is a deserializer for library class
 */
public class LibraryDeserializer implements JsonDeserializer<Library> {
    @Override
    public Library deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        // Delegate the deserialization to the context
        Playlist[] playlist = context.deserialize(jsonObject.get("playlists"), Playlist[].class);
        List<Playlist> playListList = Arrays.asList(playlist);
        final Library library = new Library();
        library.setPlaylists(playListList);
        return library;
    }
}



