package seedu.address.storage;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import seedu.address.model.Library;
import seedu.address.model.Playlist1;

/**
 * This is a deserializer for library class
 */
public class LibraryDeserializer implements JsonDeserializer<Library> {
    @Override
    public Library deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        // Delegate the deserialization to the context
        Playlist1[] playlist1 = context.deserialize(jsonObject.get("playlists"), Playlist1[].class);
        List<Playlist1> playListList = Arrays.asList(playlist1);
        final Library library = new Library();
        library.setPlaylists(playListList);
        return library;
    }
}



