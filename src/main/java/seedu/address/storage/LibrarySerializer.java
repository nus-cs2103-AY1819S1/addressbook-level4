package seedu.address.storage;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import seedu.address.model.Library;

/**
 * Serializes the library into json text.
 */
public class LibrarySerializer implements JsonSerializer<Library> {

    @Override
    public JsonElement serialize(final Library library, final Type typeOfSrc, final JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();

        final JsonElement jsonPlaylists = context.serialize(library.getPlaylists());
        jsonObject.add("playlists", jsonPlaylists);

        return jsonObject;
    }
}
